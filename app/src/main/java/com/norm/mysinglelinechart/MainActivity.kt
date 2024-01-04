package com.norm.mysinglelinechart

import co.yml.charts.common.model.Point
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.norm.mysinglelinechart.ui.theme.LightBlue
import com.norm.mysinglelinechart.ui.theme.LightGreen
import com.norm.mysinglelinechart.ui.theme.MySingleLineChartTheme
import kotlin.random.Random


const val STEPS = 10

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val pointsList = getPointsList()
            val (min, max) = getMinAndMaxPoits(pointsList)
            val xAxisData = AxisData.Builder()
                .axisStepSize(100.dp)
                .backgroundColor(Color.White)
                .steps(pointsList.size - 1)
                .labelData { i -> i.toString() + "d" }
                .labelAndAxisLinePadding(15.dp)
                .build()

            val yAxisData = AxisData.Builder()
                .steps(STEPS)
                .backgroundColor(Color.White)
                .labelAndAxisLinePadding(20.dp)
                .labelData { i ->
                    val yScale = (max - min) / STEPS.toFloat()
                    String.format("%.1f", ((i * yScale) + min))
                }.build()
            MySingleLineChartTheme {
                val lineChartData = LineChartData(
                    linePlotData = LinePlotData(
                        lines = listOf(
                            Line(
                                dataPoints = pointsList,
                                LineStyle(
                                    color = Color.Blue,
                                    width = 5f
                                ),
                                IntersectionPoint(
                                    color = LightBlue,
                                    radius = 5.dp
                                ),
                                SelectionHighlightPoint(
                                    color = Color.Green
                                ),
                                ShadowUnderLine(
                                    color = LightGreen,
                                    alpha = 1f
                                ),
                                SelectionHighlightPopUp()
                            )
                        ),
                    ),
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    gridLines = GridLines(),
                    backgroundColor = Color.White
                )
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    lineChartData = lineChartData
                )
            }
        }
    }

    private fun getPointsList(): List<Point> {
        val list = ArrayList<Point>()
        for (i in 0..31) {
            list.add(
                Point(
                    i.toFloat(),
                    Random.nextInt(60, 90).toFloat()
                )
            )
        }
        return list
    }

    private fun getMinAndMaxPoits(list: List<Point>): Pair<Float, Float> {
        var max = 0f
        var min = list[0].y
        list.forEach {
            if (max < it.y) max = it.y
            if (min > it.y) min = it.y
        }
        return Pair(min, max)
    }
}