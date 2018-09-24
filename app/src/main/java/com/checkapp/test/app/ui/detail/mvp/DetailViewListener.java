package com.checkapp.test.app.ui.detail.mvp;

import com.checkapp.test.base.BaseMVPView;
import com.checkapp.test.data.entities.Character;

public interface DetailViewListener extends BaseMVPView {
    void onCharacterInfo(Character Character);
}
