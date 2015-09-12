package mobi.cangol.mobile.demo.view;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
/**
 * @Description:
 * @version $Revision: 1.0 $
 * @author Cangol
 */
public class TabPageManager extends FragmentStatePagerAdapter implements
		TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
	private final Context mContext;
	private final TabHost mTabHost;
	private final ViewPager mViewPager;
	private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
	private final FragmentManager mFragmentManager;
	static final class TabInfo {
		private final String tag;
		private final Class<?> clss;
		private final Bundle args;
		public Fragment fragment;

		TabInfo(String _tag, Class<?> _class, Bundle _args) {
			tag = _tag;
			clss = _class;
			args = _args;
		}
	}

	static class DummyTabFactory implements TabHost.TabContentFactory {
		private final Context mContext;

		public DummyTabFactory(Context context) {
			mContext = context;
		}

		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}
	}

	public TabPageManager(FragmentManager fragmentManager, TabHost tabHost,
			ViewPager pager) {
		super(fragmentManager);
		mFragmentManager=fragmentManager;
		mContext = tabHost.getContext();
		mTabHost = tabHost;
		mViewPager = pager;
		mTabHost.setOnTabChangedListener(this);
		mViewPager.setAdapter(this);
		mViewPager.setOnPageChangeListener(this);
	}
	public Fragment getCurrentTab(){
		TabInfo info = mTabs.get(mViewPager.getCurrentItem());
		return mFragmentManager.findFragmentByTag(info.tag);
    }
	public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
		tabSpec.setContent(new DummyTabFactory(mContext));
		String tag = tabSpec.getTag();

		TabInfo info = new TabInfo(tag, clss, args);
		mTabs.add(info);
		mTabHost.addTab(tabSpec);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mTabs.size();
	}

	@Override
	public Fragment getItem(int position) {
		TabInfo info = mTabs.get(position);
		return Fragment.instantiate(mContext, info.clss.getName(), info.args);
	}

	public void onTabChanged(String tabId) {
		int position = mTabHost.getCurrentTab();
//		this.notifyDataSetChanged();
//		new Handler().post(new Runnable() {
//			@Override
//			public void run() {
//				mViewPager.setCurrentItem(position,true);
//			    }
//			});
		mViewPager.setCurrentItem(position);
	}

	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
	}

	public void onPageSelected(int position) {
		// Unfortunately when TabHost changes the current tab, it kindly
		// also takes care of putting focus on it when not in touch mode.
		// The jerk.
		// This hack tries to prevent this from pulling focus out of our
		// ViewPager.
		TabWidget widget = mTabHost.getTabWidget();
		int oldFocusability = widget.getDescendantFocusability();
		widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
		mTabHost.setCurrentTab(position);
		widget.setDescendantFocusability(oldFocusability);
	}

	public void onPageScrollStateChanged(int state) {

	}
}
