package com.example.ruxing.sliplinedemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int LINE_HEIGHT_DP = 120;

    private RecyclerView mRvCategory;
    private RecyclerView mRvMenu;
    private ImageView mIvLine;

    private List<CategoryModel> mCategoryList;
    private List<String> mMenuList;

    private MenuAdapter mMenuAdapter;
    private CategoryAdapter mCategoryAdapter;

    private int preClickPosition = 0;
    private int originalYPosition = 0;
    private int startYPosition = 0;
    private int endYPosition = 0;
    private int firstVisiblePosition;
    private int lastVisiblePosition;
    private int screenHeight;
    private int lineHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        initViews();
        originalYPosition = getYPosition(mIvLine);//获取最开始竖线所在的位置
        screenHeight = getScreenHeight();//获取屏幕的高度
        lineHeight = dp2Px(LINE_HEIGHT_DP);//获取竖线的高度
    }

    private void initDatas() {
        mCategoryList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CategoryModel model = new CategoryModel();
            model.setName("分类" + i);
            if (i == 0) {
                model.setShowLine(true);
                model.setSelected(true);
            }
            mCategoryList.add(model);
        }
        mMenuList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mMenuList.add("菜单" + i);
        }
    }

    private void initViews() {
        mRvCategory = (RecyclerView) findViewById(R.id.rv_category);
        mRvMenu = (RecyclerView) findViewById(R.id.rv_menu);
        mIvLine = (ImageView) findViewById(R.id.iv_line);
        mRvMenu.setLayoutManager(new GridLayoutManager(this, 3));
        mRvCategory.setLayoutManager(new LinearLayoutManager(this));
        mMenuAdapter = new MenuAdapter(this, mMenuList);
        mRvMenu.setAdapter(mMenuAdapter);
        mCategoryAdapter = new CategoryAdapter(this, mCategoryList);
        mRvCategory.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (preClickPosition == position) {//点击的是同一个item
                    //刷新数据
                } else {//点击的不是同一个item
                    getVisiblePosition();//获取当前可见position
                    if (preClickPosition < firstVisiblePosition) {//上次点击的item不在屏幕，已滑动到屏幕上边
                        startYPosition = -lineHeight;//动画从超出屏幕顶部的位置开始
                    } else if (preClickPosition >= firstVisiblePosition && preClickPosition <= lastVisiblePosition) {//上次点击的item在屏幕内
                        CategoryAdapter.CategoryViewHolder preClickViewHolder = (CategoryAdapter.CategoryViewHolder) mRvCategory.findViewHolderForAdapterPosition(preClickPosition);
                        if (preClickViewHolder != null) {
                            startYPosition = getYPosition(preClickViewHolder.mIvLine);//获取动画开始的位置
                        }
                    } else if (preClickPosition > lastVisiblePosition) {//上次点击的item不在屏幕，已滑动到屏幕下边
                        startYPosition = screenHeight;//动画从超出屏幕底端位置开始
                    }
                    CategoryAdapter.CategoryViewHolder currentClickViewHolder = (CategoryAdapter.CategoryViewHolder) mRvCategory.findViewHolderForAdapterPosition(position);
                    if (currentClickViewHolder != null) {
                        endYPosition = getYPosition(currentClickViewHolder.mIvLine);
                    }
                    moveLine(position);
                    preClickPosition = position;
                }
            }
        });
    }

    private void getVisiblePosition() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRvCategory.getLayoutManager();
        firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
    }

    private void moveLine(final int position) {
        mIvLine.setVisibility(View.VISIBLE);
        mCategoryAdapter.isShowLine(preClickPosition, false);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIvLine, "translationY", startYPosition-originalYPosition, endYPosition-originalYPosition);
        objectAnimator.setDuration(500);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startYPosition = endYPosition;
                mIvLine.setVisibility(View.INVISIBLE);
                mCategoryAdapter.isShowLine(position, true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }

    private int getYPosition(View view) {
        int[] positions = new int[2];
        view.getLocationOnScreen(positions);
        return positions[1];
    }

    /**
     * 获取屏幕高度
     */
    private int getScreenHeight() {
        WindowManager manager = getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    private int dp2Px(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);
    }

}
