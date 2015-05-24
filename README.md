## SwipeRefreshLayout ##
An extension of android.support.v4.widget.SwipeRefreshLayout with loading more function for ListView

## Note ##
It only serves for ListView now.

![Gif](/demo.gif)

## Use ##
Use it in your layout xml
````xml
<com.demievil.swiperefreshlayout.RefreshLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/swipe_container"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ListView
        android:id="@+id/list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#FFFFFF"
        android:dividerHeight="1px" />
</com.demievil.swiperefreshlayout.RefreshLayout>
````

Get instance and use it.
````java
mRefreshLayout.setOnRefreshLoadMoreListener(new RefreshLayout.OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        values.add(0, "Swipe Down to Refresh " + values.size());
                        mArrayAdapter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        values.add("Swipe Up to Load More "+ values.size());
                        mArrayAdapter.notifyDataSetChanged();
                        mRefreshLayout.setLoading(false);
                    }
                }, 2000);
            }
        });
````

## Thanks ##
[SwipeRefreshLayout](https://github.com/Demievil/SwipeRefreshLayout)
[MaterialLoadingProgressBar](https://github.com/lsjwzh/MaterialLoadingProgressBar)

## License ##
> Copyright (c) 2015 andforce
> 
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
> 
>    http://www.apache.org/licenses/LICENSE-2.0
> 
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.

