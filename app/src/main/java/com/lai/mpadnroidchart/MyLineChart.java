package com.lai.mpadnroidchart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.lai.mpadnroidchart.marker.DetailsMarkerView;
import com.lai.mpadnroidchart.marker.PositionMarker;
import com.lai.mpadnroidchart.marker.RoundMarker;

/**
 * @author Lai
 * @time 2018/5/26 14:27
 * @describe describe
 */

public class MyLineChart extends LineChart {

    private DetailsMarkerView mDetailsMarkerView;



    private RoundMarker mRoundMarker;
    private PositionMarker mPositionMarkerl;

    public DetailsMarkerView getDetailsMarkerView() {
        return mDetailsMarkerView;
    }

    public void setDetailsMarkerView(DetailsMarkerView detailsMarkerView) {
        mDetailsMarkerView = detailsMarkerView;
    }

    public void setRoundMarker(RoundMarker roundMarker) {
        mRoundMarker = roundMarker;
    }

    public PositionMarker getPositionMarkerl() {
        return mPositionMarkerl;
    }

    public void setPositionMarkerl(PositionMarker positionMarkerl) {
        mPositionMarkerl = positionMarkerl;
    }

    public MyLineChart(Context context) {
        super(context);
    }

    public MyLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * draws all MarkerViews on the highlighted positions
     */
    protected void drawMarkers(Canvas canvas) {

        // if there is no marker view or drawing marker is disabled
        if (mDetailsMarkerView == null || mRoundMarker == null || mPositionMarkerl == null || !isDrawMarkersEnabled() || !valuesToHighlight())
            return;

        for (int i = 0; i < mIndicesToHighlight.length; i++) {

            Highlight highlight = mIndicesToHighlight[i];

            IDataSet set = mData.getDataSetByIndex(highlight.getDataSetIndex());

            Entry e = mData.getEntryForHighlight(mIndicesToHighlight[i]);

            int entryIndex = set.getEntryIndex(e);

            // make sure entry not null
            if (e == null || entryIndex > set.getEntryCount() * mAnimator.getPhaseX())
                continue;

            float[] pos = getMarkerPosition(highlight);

            LineDataSet dataSetByIndex = (LineDataSet) getLineData().getDataSetByIndex(highlight.getDataSetIndex());

            // check bounds
            if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                continue;

            float circleRadius = dataSetByIndex.getCircleRadius();

            // callbacks to update the content
            mDetailsMarkerView.refreshContent(e, highlight);

            mDetailsMarkerView.draw(canvas, pos[0], pos[1] - mPositionMarkerl.getHeight());


            mPositionMarkerl.refreshContent(e, highlight);
            mPositionMarkerl.draw(canvas, pos[0] - mPositionMarkerl.getWidth() / 2, pos[1] - mPositionMarkerl.getHeight());

            mRoundMarker.refreshContent(e, highlight);
            mRoundMarker.draw(canvas, pos[0] - mRoundMarker.getWidth() / 2, pos[1] + circleRadius - mRoundMarker.getHeight());
        }
    }


}
