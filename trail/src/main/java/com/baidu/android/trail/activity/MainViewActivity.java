package com.baidu.android.trail.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.baidu.android.trail.R;
import com.baidu.android.trail.bean.Subject;
import com.baidu.android.trail.fragment.CollectionFragment;
import com.baidu.android.trail.fragment.DisplayFragment;
import com.baidu.android.trail.function.FunctionDrawable;
import com.baidu.android.trail.function.communication.CommunicationFragment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainViewActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private FunctionDrawable activeDrawable = FunctionDrawable.COMMUNICATION;

  private Fragment communicationFragment;
  private Fragment libraryFragment;
  private Fragment favoriteFragment;
  private Fragment errorFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_view);

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    initFunction();
    activeFunction(activeDrawable);
  }

  private void initFunction() {
    for (final FunctionDrawable drawable : FunctionDrawable.values()) {
      ImageView imageView = findViewById(drawable.getIconId());
      imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (drawable.equals(activeDrawable)) {
            return;
          }
          activeFunction(drawable);
          activeDrawable = drawable;
        }
      });
    }
  }

  private void activeFunction(FunctionDrawable functionDrawable) {
    for (FunctionDrawable drawable : FunctionDrawable.values()) {
      ImageView imageView = findViewById(drawable.getIconId());
      boolean isSelect = drawable.equals(functionDrawable);
      imageView.setImageResource(isSelect
          ? drawable.getActiveResId()
          : drawable.getDefaultResId());
    }
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.fragment_container, getFragment(functionDrawable))
        .commit();
  }

  private Fragment getFragment(FunctionDrawable functionDrawable) {
    switch (functionDrawable) {
      case COMMUNICATION:
        if (communicationFragment == null) {
          communicationFragment = new CommunicationFragment();
        }
        return communicationFragment;
      case LIBRARY:
        if (libraryFragment == null) {
          libraryFragment = new DisplayFragment();
        }
        return libraryFragment;
      case FAVORITE:
        if (favoriteFragment == null) {
          favoriteFragment = CollectionFragment.newInstance(1);
        }
        return favoriteFragment;
      case ERROR:
        if (errorFragment == null) {
          errorFragment = CollectionFragment.newInstance(2);
        }
        return errorFragment;
    }
    return null;
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main_view, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_personal) {//我的信息
    } else if (id == R.id.nav_record) {//测试记录

    } else if (id == R.id.nav_version) {//版本信息

    } else if (id == R.id.nav_feedback) {//反馈

    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  public static void testQuery() {
    BmobQuery<Subject> query = new BmobQuery<>();
    query.setLimit(20).findObjects(new FindListener<Subject>() {
      @Override
      public void done(List<Subject> list, BmobException e) {
        if (e != null) {
          e.printStackTrace();
          Log.e("tango","failed! ");
        } else {
          Log.e("tango", list.size() + "");
        }
      }
    });
  }

  public static void testUpload() {
    Subject subject1 = new Subject();
    subject1.setQuestion("在Java中，负责对字节代码解释执行的是？");
    subject1.setOptionA("A: 应用服务器");
    subject1.setOptionB("B: 虚拟机");
    subject1.setOptionC("C: 垃圾回收器");
    subject1.setOptionD("D: 编译器");
    subject1.setPicture("https://www.w3schools.in/wp-content/uploads/2014/08/Diagram-of-JVM.png");
    subject1.setAnswer(2);
    subject1.save(new SaveListener<String>() {
      @Override
      public void done(String s, BmobException e) {
        if (e == null) {
          Log.e("tango","success " + s);
        } else {
          Log.e("tango","failed! ");
        }
      }
    });
  }

}
