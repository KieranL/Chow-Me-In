package com.mysampleapp.demo;

import android.support.v4.app.Fragment;

import com.mysampleapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DemoConfiguration {

    private static final List<DemoFeature> demoFeatures = new ArrayList<DemoFeature>();

    static {
        addDemoFeature("user_identity", R.mipmap.user_identity, R.string.feature_sign_in_title,
                R.string.feature_sign_in_subtitle, R.string.feature_sign_in_overview,
                R.string.feature_sign_in_description, R.string.feature_sign_in_powered_by,
                new DemoItem(R.string.main_fragment_title_user_identity, R.mipmap.user_identity,
                        R.string.feature_sign_in_demo_button, IdentityDemoFragment.class));
        addDemoFeature("engagement", R.mipmap.engage,
            R.string.feature_user_engagement_title, R.string.feature_user_engagement_subtitle,
            R.string.feature_user_engagement_overview,
            0,
            R.string.feature_user_engagement_powered_by
            , new DemoItem(R.string.main_fragment_title_app_analytics,
                R.mipmap.analytics, R.string.feature_app_analytics_demo_button,
                AppAnalyticsDemoFragment.class)
        );
    }

    public static List<DemoFeature> getDemoFeatureList() {
        return Collections.unmodifiableList(demoFeatures);
    }

    public static DemoFeature getDemoFeatureByName(final String name) {
        for (DemoFeature demoFeature : demoFeatures) {
            if (demoFeature.name.equals(name)) {
                return demoFeature;
            }
        }
        return null;
    }

    private static void addDemoFeature(final String name, final int iconResId, final int titleResId,
                                       final int subtitleResId, final int overviewResId,
                                       final int descriptionResId, final int poweredByResId,
                                       final DemoItem... demoItems) {
        DemoFeature demoFeature = new DemoFeature(name, iconResId, titleResId, subtitleResId,
                overviewResId, descriptionResId, poweredByResId, demoItems);
        demoFeatures.add(demoFeature);
    }

    public static class DemoFeature {
        public String name;
        public int iconResId;
        public int titleResId;
        public int subtitleResId;
        public int overviewResId;
        public int descriptionResId;
        public int poweredByResId;
        public List<DemoItem> demos;

        public DemoFeature() {

        }

        public DemoFeature(final String name, final int iconResId, final int titleResId,
                           final int subtitleResId, final int overviewResId,
                           final int descriptionResId, final int poweredByResId,
                           final DemoItem... demoItems) {
            this.name = name;
            this.iconResId = iconResId;
            this.titleResId = titleResId;
            this.subtitleResId = subtitleResId;
            this.overviewResId = overviewResId;
            this.descriptionResId = descriptionResId;
            this.poweredByResId = poweredByResId;
            this.demos = Arrays.asList(demoItems);
        }
    }

    public static class DemoItem {
        public int titleResId;
        public int iconResId;
        public int buttonTextResId;
        public String fragmentClassName;

        public String title;
        public String buttonText;
        public Serializable tag ;

        public DemoItem(final int titleResId, final int iconResId, final int buttonTextResId,
                        final Class<? extends Fragment> fragmentClass) {
            this.titleResId = titleResId;
            this.iconResId = iconResId;
            this.buttonTextResId = buttonTextResId;
            this.fragmentClassName = fragmentClass.getName();
        }

        public DemoItem(final String title, final String buttonText, final Serializable tag, final Class<? extends  Fragment> fragmentClass){
            this.title = title;
            this.buttonText = buttonText;
            this.tag = tag;
            this.fragmentClassName = fragmentClass.getName();
        }
    }
}
