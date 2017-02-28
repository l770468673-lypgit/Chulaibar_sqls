package com.tianyi.chulaibar.fragment;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

/**
 * Fragment的工厂.
 */
public class FragmentFactory {

	// fragment的集合
	// private static HashMap<Integer, Fragment> fragments = new
	// HashMap<Integer, Fragment>();

	// SparseArray:这个集合的key默认就是int类型.效率比HashMap更高.
	public static SparseArray<Fragment> fragments = new SparseArray<Fragment>();

	private static final int SHOUYE_FRAGMENT = 0;
	private static final int FAXIAN_FRAGMENT = 1;
	private static final int FABU_FRAGMENT = 2;
	private static final int WODE_FRAGMENT = 3;
	private static final int GERENZHUCE_FRAGMENT = 4;
	private static final int QIYEZHUCE_FRAGMENT = 5;

	//创建Fragment对象
	public static Fragment createFragment(int position) {

		Fragment mFragment = fragments.get(position);

		if (mFragment == null) {
			switch (position) {
			case SHOUYE_FRAGMENT:
				mFragment = new ShouYeFragment();
				break;
			case FAXIAN_FRAGMENT:
				mFragment = new FaXianFragment();
				break;
			case FABU_FRAGMENT:
				mFragment = new FaBuFragment();
				break;
			case WODE_FRAGMENT:
				mFragment = new MineFragment();
				break;
				case GERENZHUCE_FRAGMENT:
					mFragment = new GeRenZhuCeFragment();
					break;
				case QIYEZHUCE_FRAGMENT:
					mFragment = new QiYeZhuCeFragment();
					break;
			}
			fragments.put(position, mFragment);
		}
		return mFragment;
	}
}
