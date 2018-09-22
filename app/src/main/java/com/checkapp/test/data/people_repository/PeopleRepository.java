package com.checkapp.test.data.people_repository;

import com.checkapp.test.data.people_repository.local.People;
import com.checkapp.test.data.people_repository.local.PeopleLocalSource;
import com.checkapp.test.data.people_repository.remote.PeopleRemoteSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PeopleRepository {
    private static PeopleRepository instance;
    private final PeopleLocalSource local;
    private final PeopleRemoteSource remote;


    public PeopleRepository() {
        this.remote = PeopleRemoteSource.getInstance();
        this.local = PeopleLocalSource.getInstance();
    }

    public static PeopleRepository getInstance() {
        if (instance == null) {
            instance = new PeopleRepository();
        }
        return instance;
    }

    public Single<List<People>> getAllPeople() {
        return Single.concat(getallPeopleLocal(),
                getallPeopleRemote()
                        .map(new Function<List<People>, List<People>>() {
                            @Override
                            public List<People> apply(List<People> peopleList) throws Exception {
                                local.insertAllPeople(peopleList);
                                return peopleList;
                            }
                        }))
                .filter(new Predicate<List<People>>() {
                    @Override
                    public boolean test(List<People> peopleList) throws Exception {
                        return !peopleList.isEmpty();
                    }
                })
                .first(new ArrayList<People>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<People>> getallPeopleRemote() {
        Timber.d("REMOTE REMOTE REMOTE");
        return remote.getAllPeople();
    }

    public Single<List<People>> getallPeopleLocal() {
        Timber.d("LOCAL LOCAL LOCAL");
        return local.getAllPeople();

    }
}
