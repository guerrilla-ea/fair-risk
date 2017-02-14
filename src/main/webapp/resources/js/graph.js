
var margin = {top: 20, right: 20, bottom: 30, left: 100},
	    width = 500 - margin.left - margin.right,
	    height = 400 - margin.top - margin.bottom;

var x = d3.scale.log()
	.range([1,width])
	.domain([.01,100]);

var y = d3.scale.log()
	.range([height,1])
	.domain([1000.00,100000000.00]);

function loadGraph(data){

	var color = ["blue", "red"];
	var type = ["Primary Risk", "Secondary Risk"];
	
	var xAxis = d3.svg.axis()
	    .scale(x)
	    .orient("bottom")
	    .ticks(5,d3.format(".02f"));
	
	var yAxis = d3.svg.axis()
	    .scale(y)
	    .orient("left")
	    .ticks(6,d3.format("$,"));
	
	var svg = d3.select("#graph").append("svg")
	    .attr("width", width + margin.left + margin.right)
	    .attr("height", height + margin.top + margin.bottom)
	  .append("g")
	    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	
	svg.append("g")
	.attr("class", "x axis")
	.attr("transform", "translate(0," + height + ")")
	.call(xAxis)
	.append("text")
	.attr("class", "label")
	.attr("x", width)
	.attr("y", -6)
	.style("text-anchor", "end")
	.text("Loss Event Frequency");
	
	svg.append("g")
	.attr("class", "y axis")
	.call(yAxis)
	.append("text")
	.attr("class", "label")
	.attr("transform", "rotate(-90)")
	.attr("y", 6)
	.attr("dy", ".71em")
	.style("text-anchor", "end")
	.text("Loss Magnitue ($)");
	
	var legend = svg.selectAll(".legend")
	.data(color)
	.enter().append("g")
	.attr("class", "legend")
	.attr("transform", function(d, i) { return "translate(0," + i * 20 + ")"; });
	
	legend.append("rect")
	.attr("x", width - 18)
	.attr("width", 18)
	.attr("height", 18)
	.style("fill", function(d,i){ return color[i];});
	
	legend.append("text")
	.attr("x", width - 24)
	.attr("y", 9)
	.attr("dy", ".35em")
	.style("text-anchor", "end")
	.text(function(d,i) { return type[i]; });

	//d3.json("http://fair-risk.itos.redhat.com/services/risk/exampleAnalysis", function(error, data){
		data.primaryRisk.samples.forEach(function(d){
			d.x=d.x;
			d.y=d.y;
		});
		
		if(data.secondaryRisk){
			data.secondaryRisk.samples.forEach(function(d){
				d.x=d.x;
				d.y=d.y;
			});
		}
	
	  svg.selectAll(".dot")
	      .data(data.primaryRisk.samples)
	    .enter().append("circle")
	      .attr("class", "dot")
	      .attr("r", 1.5)
	      .attr("cx", function(d) { return x(d.x); })
	      .attr("cy", function(d) { return y(d.y); })
	      .style("fill", function(d) { return color[0]; });
	
	
	  
	  if(data.secondaryRisk){
		  svg.selectAll(".dot2")
		  	.data(data.secondaryRisk.samples)
		  .enter().append("circle")
		  .attr("class", "dot")
		  .attr("r", 1.5)
		  .attr("cx", function(d) { return x(d.x); })
		  .attr("cy", function(d) { return y(d.y); })
		  .style("fill", function(d) { return color[1]; });
		
		  svg.select("g")
			.append("circle")
			.attr("id", "secondMode")
			.attr("class", "dot")
			.attr("r", 3.0)
			.attr("cx", x(data.secondaryRisk.lossFrequency.mode))
			.attr("cy", 100000)
			.style("fill", "black");
		  }
	  complete(data);
	  
	//});
}


//adds the mode indicator to the graph
function complete(dd){
	
	var svg = d3.select("svg").select("g");
	
	svg.append("circle")
	.attr("id", "primaryMode")
	.attr("class", "dot")
	.attr("r", 3.0)
	.attr("cx", x(dd.primaryRisk.lossFrequency.mode))
	.attr("cy", y(dd.primaryRisk.lossMagnitude.mode))
	.style("fill", "black");
	
	if(dd.secondaryRisk){
		svg.append("circle")
		.attr("id", "secondaryMode")
		.attr("class", "dot")
		.attr("r", 3.0)
		.attr("cx", x(dd.secondaryRisk.lossFrequency.mode))
		.attr("cy", y(dd.secondaryRisk.lossMagnitude.mode))
		.style("fill", "black");
	}

}
