//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.navigationdemo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDestination.ClassType;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import androidx.navigation.Navigator.Name;
import androidx.navigation.NavigatorProvider;

import com.example.navigationdemo.R;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

@Name("fragment")
public class FragmentNavigator extends Navigator<FragmentNavigator.Destination> {
    private static final String TAG = "FragmentNavigator";
    private static final String KEY_BACK_STACK_IDS = "androidx-nav-fragment:navigator:backStackIds";
    private final Context mContext;
    final FragmentManager mFragmentManager;
    private final int mContainerId;
    ArrayDeque<Integer> mBackStack = new ArrayDeque();
    boolean mIsPendingBackStackOperation = false;
    private final OnBackStackChangedListener mOnBackStackChangedListener = new OnBackStackChangedListener() {
        @SuppressLint("RestrictedApi")
        @Override
        public void onBackStackChanged() {
            if (FragmentNavigator.this.mIsPendingBackStackOperation) {
                FragmentNavigator.this.mIsPendingBackStackOperation = !FragmentNavigator.this.isBackStackEqual();
            } else {
                int newCount = FragmentNavigator.this.mFragmentManager.getBackStackEntryCount() + 1;
                if (newCount < FragmentNavigator.this.mBackStack.size()) {
                    while (FragmentNavigator.this.mBackStack.size() > newCount) {
                        FragmentNavigator.this.mBackStack.removeLast();
                    }

                    FragmentNavigator.this.dispatchOnNavigatorBackPress();
                }

            }
        }
    };

    public FragmentNavigator(@NonNull Context context, @NonNull FragmentManager manager, int containerId) {
        this.mContext = context;
        this.mFragmentManager = manager;
        this.mContainerId = containerId;
    }

    @Override
    protected void onBackPressAdded() {
        this.mFragmentManager.addOnBackStackChangedListener(this.mOnBackStackChangedListener);
    }

    @Override
    protected void onBackPressRemoved() {
        this.mFragmentManager.removeOnBackStackChangedListener(this.mOnBackStackChangedListener);
    }

    @Override
    public boolean popBackStack() {
        if (this.mBackStack.isEmpty()) {
            return false;
        } else if (this.mFragmentManager.isStateSaved()) {
            Log.i("FragmentNavigator", "Ignoring popBackStack() call: FragmentManager has already saved its state");
            return false;
        } else {
            if (this.mFragmentManager.getBackStackEntryCount() > 0) {
                this.mFragmentManager.popBackStack(this.generateBackStackName(this.mBackStack.size(), (Integer) this.mBackStack.peekLast()), 1);
                this.mIsPendingBackStackOperation = true;
            }

            this.mBackStack.removeLast();
            return true;
        }
    }

    @Override
    @NonNull
    public FragmentNavigator.Destination createDestination() {
        return new FragmentNavigator.Destination(this);
    }

    @NonNull
    public Fragment instantiateFragment(@NonNull Context context, @NonNull FragmentManager fragmentManager, @NonNull String className, @Nullable Bundle args) {
        return Fragment.instantiate(context, className, args);
    }

    @Override
    @Nullable
    public NavDestination navigate(@NonNull FragmentNavigator.Destination destination, @Nullable Bundle args, @Nullable NavOptions navOptions, @Nullable androidx.navigation.Navigator.Extras navigatorExtras) {
        if (this.mFragmentManager.isStateSaved()) {
            Log.i("FragmentNavigator", "Ignoring navigate() call: FragmentManager has already saved its state");
            return null;
        } else {
            String className = destination.getClassName();
            if (className.charAt(0) == '.') {
                className = this.mContext.getPackageName() + className;
            }

            FragmentTransaction ft = this.mFragmentManager.beginTransaction();
            int enterAnim = navOptions != null ? navOptions.getEnterAnim() : -1;
            int exitAnim = navOptions != null ? navOptions.getExitAnim() : -1;
            int popEnterAnim = navOptions != null ? navOptions.getPopEnterAnim() : -1;
            int popExitAnim = navOptions != null ? navOptions.getPopExitAnim() : -1;
            if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
                enterAnim = enterAnim != -1 ? enterAnim : 0;
                exitAnim = exitAnim != -1 ? exitAnim : 0;
                popEnterAnim = popEnterAnim != -1 ? popEnterAnim : 0;
                popExitAnim = popExitAnim != -1 ? popExitAnim : 0;
                ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);
            }

            String tag = String.valueOf(destination.getId());
            Fragment frag = mFragmentManager.findFragmentByTag(tag);
            if (frag == null) {
                frag = this.instantiateFragment(this.mContext, this.mFragmentManager, className, args);
                frag.setArguments(args);
            }
            if (mFragmentManager.getFragments().size() > 0) {
                Fragment lastFragment = mFragmentManager.getFragments().get(mFragmentManager.getFragments().size() - 1);
                ft.hide(lastFragment);
                //添加这行，保证fragment生命周期回调
                //ft.setMaxLifecycle(lastFragment, Lifecycle.State.STARTED);
                if (frag.isAdded()) {
                    ft.show(frag);
                } else {
                    ft.add(mContainerId, frag, tag);
                }
            } else {
                ft.replace(mContainerId, frag);
            }
            ft.setPrimaryNavigationFragment(frag);
            int destId = destination.getId();
            boolean initialNavigation = this.mBackStack.isEmpty();
            boolean isSingleTopReplacement = navOptions != null && !initialNavigation && navOptions.shouldLaunchSingleTop() && (Integer) this.mBackStack.peekLast() == destId;
            boolean isAdded;
            if (initialNavigation) {
                isAdded = true;
            } else if (isSingleTopReplacement) {
                if (this.mBackStack.size() > 1) {
                    this.mFragmentManager.popBackStack(this.generateBackStackName(this.mBackStack.size(), (Integer) this.mBackStack.peekLast()), 1);
                    ft.addToBackStack(this.generateBackStackName(this.mBackStack.size(), destId));
                    this.mIsPendingBackStackOperation = true;
                }

                isAdded = false;
            } else {
                ft.addToBackStack(this.generateBackStackName(this.mBackStack.size() + 1, destId));
                this.mIsPendingBackStackOperation = true;
                isAdded = true;
            }

            if (navigatorExtras instanceof FragmentNavigator.Extras) {
                FragmentNavigator.Extras extras = (FragmentNavigator.Extras) navigatorExtras;
                Iterator var17 = extras.getSharedElements().entrySet().iterator();

                while (var17.hasNext()) {
                    Entry<View, String> sharedElement = (Entry) var17.next();
                    ft.addSharedElement((View) sharedElement.getKey(), (String) sharedElement.getValue());
                }
            }

            ft.setReorderingAllowed(true);
            ft.commit();
            if (isAdded) {
                this.mBackStack.add(destId);
                return destination;
            } else {
                return null;
            }
        }
    }

    @Override
    @Nullable
    public Bundle onSaveState() {
        Bundle b = new Bundle();
        int[] backStack = new int[this.mBackStack.size()];
        int index = 0;

        Integer id;
        for (Iterator var4 = this.mBackStack.iterator(); var4.hasNext(); backStack[index++] = id) {
            id = (Integer) var4.next();
        }

        b.putIntArray("androidx-nav-fragment:navigator:backStackIds", backStack);
        return b;
    }

    @Override
    public void onRestoreState(@Nullable Bundle savedState) {
        if (savedState != null) {
            int[] backStack = savedState.getIntArray("androidx-nav-fragment:navigator:backStackIds");
            if (backStack != null) {
                this.mBackStack.clear();
                int[] var3 = backStack;
                int var4 = backStack.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    int destId = var3[var5];
                    this.mBackStack.add(destId);
                }
            }
        }

    }

    @NonNull
    private String generateBackStackName(int backStackIndex, int destId) {
        return backStackIndex + "-" + destId;
    }

    private int getDestId(@Nullable String backStackName) {
        String[] split = backStackName != null ? backStackName.split("-") : new String[0];
        if (split.length != 2) {
            throw new IllegalStateException("Invalid back stack entry on the NavHostFragment's back stack - use getChildFragmentManager() if you need to do custom FragmentTransactions from within Fragments created via your navigation graph.");
        } else {
            try {
                Integer.parseInt(split[0]);
                return Integer.parseInt(split[1]);
            } catch (NumberFormatException var4) {
                throw new IllegalStateException("Invalid back stack entry on the NavHostFragment's back stack - use getChildFragmentManager() if you need to do custom FragmentTransactions from within Fragments created via your navigation graph.");
            }
        }
    }

    boolean isBackStackEqual() {
        int fragmentBackStackCount = this.mFragmentManager.getBackStackEntryCount();
        if (this.mBackStack.size() != fragmentBackStackCount + 1) {
            return false;
        } else {
            Iterator<Integer> backStackIterator = this.mBackStack.descendingIterator();
            int fragmentBackStackIndex = fragmentBackStackCount - 1;

            while (backStackIterator.hasNext() && fragmentBackStackIndex >= 0) {
                int destId = (Integer) backStackIterator.next();

                try {
                    int fragmentDestId = this.getDestId(this.mFragmentManager.getBackStackEntryAt(fragmentBackStackIndex--).getName());
                    if (destId != fragmentDestId) {
                        return false;
                    }
                } catch (NumberFormatException var6) {
                    throw new IllegalStateException("Invalid back stack entry on the NavHostFragment's back stack - use getChildFragmentManager() if you need to do custom FragmentTransactions from within Fragments created via your navigation graph.");
                }
            }

            return true;
        }
    }

    public static final class Extras implements androidx.navigation.Navigator.Extras {
        private final LinkedHashMap<View, String> mSharedElements = new LinkedHashMap();

        Extras(Map<View, String> sharedElements) {
            this.mSharedElements.putAll(sharedElements);
        }

        @NonNull
        public Map<View, String> getSharedElements() {
            return Collections.unmodifiableMap(this.mSharedElements);
        }

        public static final class Builder {
            private final LinkedHashMap<View, String> mSharedElements = new LinkedHashMap();

            public Builder() {
            }

            @NonNull
            public FragmentNavigator.Extras.Builder addSharedElements(@NonNull Map<View, String> sharedElements) {
                Iterator var2 = sharedElements.entrySet().iterator();

                while (var2.hasNext()) {
                    Entry<View, String> sharedElement = (Entry) var2.next();
                    View view = (View) sharedElement.getKey();
                    String name = (String) sharedElement.getValue();
                    if (view != null && name != null) {
                        this.addSharedElement(view, name);
                    }
                }

                return this;
            }

            @NonNull
            public FragmentNavigator.Extras.Builder addSharedElement(@NonNull View sharedElement, @NonNull String name) {
                this.mSharedElements.put(sharedElement, name);
                return this;
            }

            @NonNull
            public FragmentNavigator.Extras build() {
                return new FragmentNavigator.Extras(this.mSharedElements);
            }
        }
    }

    @ClassType(Fragment.class)
    public static class Destination extends NavDestination {
        private String mClassName;

        public Destination(@NonNull NavigatorProvider navigatorProvider) {
            this(navigatorProvider.getNavigator(FragmentNavigator.class));
        }

        public Destination(@NonNull Navigator<? extends FragmentNavigator.Destination> fragmentNavigator) {
            super(fragmentNavigator);
        }

        @Override
        @CallSuper
        public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs) {
            super.onInflate(context, attrs);
            TypedArray a = context.getResources().obtainAttributes(attrs, R.styleable.FragmentNavigator);
            String className = a.getString(R.styleable.FragmentNavigator_android_name);
            if (className != null) {
                this.setClassName(className);
            }

            a.recycle();
        }

        @NonNull
        public final FragmentNavigator.Destination setClassName(@NonNull String className) {
            this.mClassName = className;
            return this;
        }

        @NonNull
        public final String getClassName() {
            if (this.mClassName == null) {
                throw new IllegalStateException("Fragment class was not set");
            } else {
                return this.mClassName;
            }
        }
    }
}
