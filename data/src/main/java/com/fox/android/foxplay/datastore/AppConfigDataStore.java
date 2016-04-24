package com.fox.android.foxplay.datastore;

import com.fox.android.foxplay.model.AppLanguageEntity;
import com.fox.android.foxplay.model.ChannelEntity;
import com.fox.android.foxplay.model.IntroSlideEntity;
import com.fox.android.foxplay.model.PageEntity;
import com.fox.android.foxplay.model.SettingsEntity;

import java.util.List;

/**
 * Created by Dong (nguyen.dong@2359media.com) on 2/19/16.
 */
public interface AppConfigDataStore {
    List<PageEntity> getPages() throws Exception;

    List<IntroSlideEntity> getIntroSlides() throws Exception;

    List<ChannelEntity> getChannels() throws Exception;

    PageEntity getPage(int pageId) throws Exception;

    SettingsEntity getSettings() throws Exception;
}
