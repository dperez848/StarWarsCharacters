package com.checkapp.test.app.ui.home.mvp;

import com.checkapp.test.R;
import com.checkapp.test.app.App;
import com.checkapp.test.base.BasePresenter;
import com.checkapp.test.data.people_repository.PeopleRepository;
import com.checkapp.test.data.people_repository.local.People;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class PresenterFrgHome<V extends HomeViewListener> extends BasePresenter<V>
        implements HomePresenterListener<V> {

    public void doGetItems() {
        getCompositeDisposable().add(
                PeopleRepository.getInstance().getAllPeople()
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
                        .subscribe(new Consumer<List<People>>() {
                                       @Override
                                       public void accept(List<People> peopleList) throws Exception {
                                           getMvpView().onItemsDone(peopleList);
                                       }
                                   }
                                , new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {

                                    }
                                }));
    }

}
