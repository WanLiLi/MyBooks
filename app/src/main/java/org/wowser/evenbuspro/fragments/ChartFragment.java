package org.wowser.evenbuspro.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.wowser.evenbuspro.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by wanli on 2016/5/20.
 */
public class ChartFragment extends BaseFragments {
    public static String TAG = "ChartFragment";
    @Bind(R.id.lineChartView)
    LineChartView lineChartView;

    private View rootView;


    public static ChartFragment newInstance(){
        return  new ChartFragment();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_hello_chart, container, false);
        ButterKnife.bind(this, rootView);


        initView();
        initEvEnt();
        return rootView;
    }

    public void initView() {
        //lineChartView.setZoomEnabled(false);

        lineChartView.setOnValueTouchListener(new LineChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int i, int i1, PointValue pointValue) {
                Toast.makeText(getActivity(), String.valueOf(" x:"+pointValue.getX()+" y:"+pointValue.getY()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {

            }
        });


        List<Line> lines = new ArrayList<Line>();
        List<PointValue> values = new ArrayList<PointValue>();  //Point    money
        List<AxisValue>  axValues = new ArrayList<AxisValue>(); //Axis     day

        for (int i = 0; i < 10; i++) {
            values.add(new PointValue(i, i));        //money

            AxisValue axisValue = new AxisValue(i).setLabel("date");
//            Axis axisX = new Axis();
            axValues.add(axisValue);
        }


        Line line = new Line(values);
        line.setColor(getResources().getColor(R.color.red));
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(false);
        line.setFilled(false);
        line.setHasLabels(false);
        line.setHasLabelsOnlyForSelected(false);
        line.setHasLines(true);
        line.setHasPoints(true);
        lines.add(line);

        LineChartData data = new LineChartData(lines);


        Axis axisX = new Axis(axValues);
        //axisX.setName("axisX");

         Axis axisY = new Axis(axValues);
        //axisY.setHasLines(true);
        //axisY.setName("axisY");

        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChartView.setLineChartData(data);



        int top = (int)100;
        int n = (int)Math.log10(top);
        int num = (int)Math.pow(10, n);
        int maxNum = num * 10;
        top = maxNum;
        for (int i = num; i < maxNum; i=i+num) {
            if(i > 100) {
                top = i;
                break;
            }
        }


        //不然不显示
        final Viewport v = new Viewport(lineChartView.getMaximumViewport());
        v.bottom = 0;
        v.top = top;
        v.left = 0;
        v.right = 5 - 1;
        lineChartView.setMaximumViewport(v);
        lineChartView.setCurrentViewport(v);

    }

    private void initEvEnt() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
