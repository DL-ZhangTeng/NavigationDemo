//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.navigationdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.CallSuper;
import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;

import com.example.navigationdemo.R;

public class NavHostFragment extends Fragment implements NavHost {
    private static final String KEY_GRAPH_ID = "android-support-nav:fragment:graphId";
    private static final String KEY_START_DESTINATION_ARGS = "android-support-nav:fragment:startDestinationArgs";
    private static final String KEY_NAV_CONTROLLER_STATE = "android-support-nav:fragment:navControllerState";
    private static final String KEY_DEFAULT_NAV_HOST = "android-support-nav:fragment:defaultHost";
    private NavController mNavController;
    private int mGraphId;
    private boolean mDefaultNavHost;

    public NavHostFragment() {
    }

    @NonNull
    public static NavController findNavController(@NonNull Fragment fragment) {
        for (Fragment findFragment = fragment; findFragment != null; findFragment = findFragment.getParentFragment()) {
            if (findFragment instanceof NavHostFragment) {
                return ((NavHostFragment) findFragment).getNavController();
            }

            Fragment primaryNavFragment = findFragment.requireFragmentManager().getPrimaryNavigationFragment();
            if (primaryNavFragment instanceof NavHostFragment) {
                return ((NavHostFragment) primaryNavFragment).getNavController();
            }
        }

        View view = fragment.getView();
        if (view != null) {
            return Navigation.findNavController(view);
        } else {
            throw new IllegalStateException("Fragment " + fragment + " does not have a NavController set");
        }
    }

    @NonNull
    public static NavHostFragment create(@NavigationRes int graphResId) {
        return create(graphResId, (Bundle) null);
    }

    @NonNull
    public static NavHostFragment create(@NavigationRes int graphResId, @Nullable Bundle startDestinationArgs) {
        Bundle b = null;
        if (graphResId != 0) {
            b = new Bundle();
            b.putInt("android-support-nav:fragment:graphId", graphResId);
        }

        if (startDestinationArgs != null) {
            if (b == null) {
                b = new Bundle();
            }

            b.putBundle("android-support-nav:fragment:startDestinationArgs", startDestinationArgs);
        }

        NavHostFragment result = new NavHostFragment();
        if (b != null) {
            result.setArguments(b);
        }

        return result;
    }

    @Override
    @NonNull
    public final NavController getNavController() {
        if (this.mNavController == null) {
            throw new IllegalStateException("NavController is not available before onCreate()");
        } else {
            return this.mNavController;
        }
    }

    @Override
    @CallSuper
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (this.mDefaultNavHost) {
            this.requireFragmentManager().beginTransaction().setPrimaryNavigationFragment(this).commit();
        }

    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this.requireContext();
        this.mNavController = new NavController(context);
        this.mNavController.getNavigatorProvider().addNavigator(this.createFragmentNavigator());
        Bundle navState = null;
        if (savedInstanceState != null) {
            navState = savedInstanceState.getBundle("android-support-nav:fragment:navControllerState");
            if (savedInstanceState.getBoolean("android-support-nav:fragment:defaultHost", false)) {
                this.mDefaultNavHost = true;
                this.requireFragmentManager().beginTransaction().setPrimaryNavigationFragment(this).commit();
            }
        }

        if (navState != null) {
            this.mNavController.restoreState(navState);
        }

        if (this.mGraphId != 0) {
            this.mNavController.setGraph(this.mGraphId);
        } else {
            Bundle args = this.getArguments();
            int graphId = args != null ? args.getInt("android-support-nav:fragment:graphId") : 0;
            Bundle startDestinationArgs = args != null ? args.getBundle("android-support-nav:fragment:startDestinationArgs") : null;
            if (graphId != 0) {
                this.mNavController.setGraph(graphId, startDestinationArgs);
            }
        }

    }

    @NonNull
    protected Navigator<? extends FragmentNavigator.Destination> createFragmentNavigator() {
        return new FragmentNavigator(this.requireContext(), this.getChildFragmentManager(), this.getId());
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout frameLayout = new FrameLayout(inflater.getContext());
        frameLayout.setId(this.getId());
        return frameLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!(view instanceof ViewGroup)) {
            throw new IllegalStateException("created host view " + view + " is not a ViewGroup");
        } else {
            View rootView = view.getParent() != null ? (View) view.getParent() : view;
            Navigation.setViewNavController(rootView, this.mNavController);
        }
    }

    @Override
    @CallSuper
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavHostFragment);
        int graphId = a.getResourceId(R.styleable.NavHostFragment_navGraph, 0);
        boolean defaultHost = a.getBoolean(R.styleable.NavHostFragment_defaultNavHost, false);
        if (graphId != 0) {
            this.mGraphId = graphId;
        }

        if (defaultHost) {
            this.mDefaultNavHost = true;
        }

        a.recycle();
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle navState = this.mNavController.saveState();
        if (navState != null) {
            outState.putBundle("android-support-nav:fragment:navControllerState", navState);
        }

        if (this.mDefaultNavHost) {
            outState.putBoolean("android-support-nav:fragment:defaultHost", true);
        }

    }
}
