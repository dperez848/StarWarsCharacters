package com.checkapp.test.data.repository.people;

import android.annotation.SuppressLint;

import com.checkapp.test.data.entities.Character;
import com.checkapp.test.data.repository.people.local.CharacterLocalSource;
import com.checkapp.test.data.repository.people.remote.CharacterRemoteSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CharacterRepository {
    private static CharacterRepository instance;
    private final CharacterLocalSource local;
    private final CharacterRemoteSource remote;


    public CharacterRepository() {
        this.remote = CharacterRemoteSource.getInstance();
        this.local = CharacterLocalSource.getInstance();
    }

    public static CharacterRepository getInstance() {
        if (instance == null) {
            instance = new CharacterRepository();
        }
        return instance;
    }

    public Single<List<Character>> getAllCharacter() {
        return Single.concat(getAllCharacterLocal(),
                getAllCharacterRemote()
                        .map(new Function<List<Character>, List<Character>>() {
                            @Override
                            public List<Character> apply(List<Character> CharacterList) throws Exception {
                                local.insertAllCharacter(CharacterList);
                                return CharacterList;
                            }
                        }))
                .filter(new Predicate<List<Character>>() {
                    @Override
                    public boolean test(List<Character> CharacterList) throws Exception {
                        return !CharacterList.isEmpty();
                    }
                })
                .first(new ArrayList<Character>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Single<List<Character>> getAllCharacterRemote() {
        return remote.getAllCharacter();
    }

    private Single<List<Character>> getAllCharacterLocal() {
        return local.getAllCharacter();
    }

    @SuppressLint("CheckResult")
    public void updateCharacter(Character Character) {
      local.updateCharacter(Character).subscribe(new Action() {
            @Override
            public void run() throws Exception {
                Timber.d("Character added successfully");
            }
        });
    }
}
