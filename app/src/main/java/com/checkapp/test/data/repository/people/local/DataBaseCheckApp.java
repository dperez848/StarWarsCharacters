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

package com.checkapp.test.data.repository.people.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.checkapp.test.app.App;
import com.checkapp.test.data.repository.Character.local.CharacterDao;
import com.checkapp.test.data.repository.people.local.models.CharacterLocal;

@Database(entities = {CharacterLocal.class}, version = 1, exportSchema = false)
public abstract class DataBaseCheckApp extends RoomDatabase {

    private static final String DATABASE = "populus-database";
    private static volatile DataBaseCheckApp INSTANCE;

    public abstract CharacterDao CharacterDao();

    public static DataBaseCheckApp getInstance() {
        if (INSTANCE == null) {
            synchronized (DataBaseCheckApp.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(App.getGlobalContext(),
                            DataBaseCheckApp.class, DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
