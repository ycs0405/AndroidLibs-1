/*
 * AuthorUtli     2016/9/25-09-25
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.share;

import android.app.Activity;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by Koterwong on 2016/9/25 18:41
 */
public class AuthorUtil {
  private static AuthorUtil sAuthorUtil = new AuthorUtil();

  public static AuthorUtil with() {
    return sAuthorUtil;
  }

  public void authorQQ(Activity activity, UMAuthListener umAuthListener) {
    UMShareAPI mShareAPI = UMShareAPI.get(activity);
    mShareAPI.doOauthVerify(activity, SHARE_MEDIA.QQ, umAuthListener);
  }

  public void authorWx(Activity activity, UMAuthListener umAuthListener) {
    UMShareAPI mShareAPI = UMShareAPI.get(activity);
    mShareAPI.doOauthVerify(activity, SHARE_MEDIA.QQ, umAuthListener);
  }

  public void authorSina(Activity activity, UMAuthListener umAuthListener) {
    UMShareAPI mShareAPI = UMShareAPI.get(activity);
    mShareAPI.doOauthVerify(activity, SHARE_MEDIA.QQ, umAuthListener);
  }
}
