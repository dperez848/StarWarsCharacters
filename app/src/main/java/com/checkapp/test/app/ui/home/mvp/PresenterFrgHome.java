package com.checkapp.test.app.ui.home.mvp;

import com.checkapp.test.R;
import com.checkapp.test.app.App;
import com.checkapp.test.base.BasePresenter;
import com.checkapp.test.data.entities.Character;
import com.checkapp.test.data.repository.people.CharacterRepository;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class PresenterFrgHome<V extends HomeViewListener> extends BasePresenter<V>
        implements HomePresenterListener<V> {

    private List<Character> CharacterListArray = new ArrayList<>();
    private JsonAdapter<Character> CharacterAdapter =
            new Moshi.Builder().build().adapter(Character.class);

    public void doGetItems() {
        getCompositeDisposable().add(
                CharacterRepository.getInstance().getAllCharacter()
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                getMvpView().showLoading(App.getGlobalContext().getString(R.string.common_loading));
                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                getMvpView().hideLoading();
                            }
                        })
                        .subscribe(new Consumer<List<Character>>() {
                                       @Override
                                       public void accept(List<Character> CharacterList) throws Exception {
                                           CharacterListArray = CharacterList;
                                           getMvpView().onItemsDone(CharacterListArray);
                                       }
                                   }
                                , new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {

                                    }
                                }));
    }

    public void setFavoriteToCharacter(Character Character) {
        Character.setFavorite(!Character.isFavorite());
        saveFavoriteToDB(Character);

        for (Character CharacterUnit : CharacterListArray) {
            if (CharacterUnit.getName().equals(Character.getName())) {
                CharacterUnit.setFavorite(Character.isFavorite());
            }
        }
        getMvpView().onRefreshAdapterItems(CharacterListArray);
    }

    private void saveFavoriteToDB(Character Character) {
        CharacterRepository.getInstance().updateCharacter(Character);
    }

    public void doSetAllItemsToAdapter() {
        getMvpView().onRefreshAdapterItems(CharacterListArray);
    }

    public void doGoToDetail(Character Character) {
        getMvpView().onGoToDetail(CharacterAdapter.toJson(Character));
    }
}
