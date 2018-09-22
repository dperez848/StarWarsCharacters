/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.checkapp.test.data.people_repository.local;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Using the Room database as a data source.
 */
public class PeopleLocalSource {

    private static PeopleLocalSource instance;
    private PeopleDao dao;

    private PeopleLocalSource() {
        this.dao = DataBaseCheckApp.getInstance().peopleDao();
    }

    public static PeopleLocalSource getInstance() {
        if (instance == null) {
            instance = new PeopleLocalSource();
        }
        return instance;
    }

    public Single<List<People>> getAllPeople() {
        return dao.getAllPeople()
                .flatMap(getMapper())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void insertAllPeople(final List<People> peopleList) {
        Completable
                .fromAction(new Action() {
                    @Override
                    public void run() throws Exception {
                        dao.insertAll(transformPeopletoPeopleLocal(peopleList));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }


    private List<PeopleLocal> transformPeopletoPeopleLocal(List<People> peopleList) {
        List<PeopleLocal> peopleLocalList = new ArrayList<>();
        for (People peopleLocal : peopleList) {
            peopleLocalList.add(PeopleLocal.fromPeopletoPeopleLocal(peopleLocal));
        }
        return peopleLocalList;
    }
    private List<People> transformPeopleLocalToPeople(List<PeopleLocal> peopleLocalList) {
        List<People> peopleList = new ArrayList<>();
        for (PeopleLocal peopleLocal : peopleLocalList) {
            peopleList.add(People.fromPeopleLocal(peopleLocal));
        }
        return peopleList;
    }

    @NonNull
    private Function<List<PeopleLocal>, SingleSource<? extends List<People>>> getMapper() {
        return new Function<List<PeopleLocal>, SingleSource<? extends List<People>>>() {
            @Override
            public SingleSource<? extends List<People>> apply(List<PeopleLocal> peopleList) throws Exception {
                return Single.just(transformPeopleLocalToPeople(peopleList));
            }
        };
    }
}
