/**
 * 定义一个Charts类，存放一些常用的统计图
 * 
 * @author jiangbo
 */
var Charts = function() {

};

//  饼图加载
Charts.pie = function(id, option) {
  require(
          [
              'echarts',
              'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
          ],
          function (ec) {
              // 基于准备好的dom，初始化echarts图表
              var myChart = ec.init(document.getElementById(id)); 
              // 为echarts对象加载数据 
              myChart.setOption(option); 
          }
      );
}

// 饼图样式一
Charts.pieType1 = function(title, subtext, menuItem, showTitle, dataItem) {
	option = {
		    title : {
		        text: title,
		        subtext: subtext,
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data: menuItem
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:showTitle,
		            type:'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:dataItem
		        }
		    ]
		};
	return option;
}