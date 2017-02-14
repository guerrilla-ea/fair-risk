/**
 * Build the simple viewpoint of risk level.
 */



function buildMatrix(dd) {

	var margin = { left: 120, right: 20, top: 20, bottom: 100};
	var width = 600 - margin.left - margin.right;
	var height = 250 - margin.top;
	
	var gwidth = 60;
	var gheight = 40;

	var svg = d3.select("#grid-container").append("svg")
		.attr("id", "the-matrix")
		.attr("width", width)
	    .attr("height", height + margin.top + margin.bottom)
	    .append("g")
	    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	var cells =  [
	              [{text:"M", color:"#FFFF00"},  {text:"H", color:"#FF9900"}, {text:"VH", color:"#FF0000"}, {text:"VH", color:"#FF0000"}, {text:"VH", color:"#FF0000"} ],
	              [{text:"L", color:"#66FF00"},  {text:"M", color:"#FFFF00"}, {text:"H", color:"#FF9900"}, {text:"VH", color:"#FF0000"}, {text:"VH", color:"#FF0000"}],
	              [{text:"VL", color:"#339900"}, {text:"L", color:"#66FF00"}, {text:"M", color:"#FFFF00"}, {text:"H", color:"#FF9900"}, {text:"VH", color:"#FF0000"}],
	              [{text:"VL", color:"#339900"}, {text:"VL", color:"#339900"}, {text:"L", color:"#66FF00"}, {text:"M", color:"#FFFF00"}, {text:"H", color:"#FF9900"}],
	              [{text:"VL", color:"#339900"}, {text:"VL", color:"#339900"}, {text:"VL", color:"#339900"}, {text:"L", color:"#66FF00"}, {text:"M", color:"#FFFF00"}]
	              ];
	
	var rowLabels = [
	                 	"$10M",
	                 	"$1M",
	                 	"$100k",
	                 	"$10k",
	                 	"$1k"
	                 ];
	
	var colLabels = [
	                 	".001",
	                 	".01",
	                 	".1",
	                 	"1",
	                 	"10"
	                 ];
	
	

	//svg.append("g").selectAll("rect").data(cells)
	
	for(var row=0; row<cells.length; row++){
		for(var col=0; col<cells[row].length; col++){
			svg.append("rect").
				attr("x", col*gwidth).
				attr("y", row*gheight).
				attr("height", gheight).
				attr("width", gwidth).
				style("fill", cells[row][col].color);
		}
	}
	
	svg.selectAll("g").data(rowLabels)
		.enter().append("text")
		.attr("y", function(d, i){ return 15+(gheight*i);})
		.attr("x", "-15")
		.attr("dy", ".7em")
		.style("text-anchor", "end")
		.text(function(d,i) { return d; });
	
	svg.selectAll("g").data(colLabels)
		.enter().append("text")
		.attr("y", height-15)
		.attr("x", function(d,i){return 40+gwidth*i;})
		.attr("dy", ".7em")
		.style("text-anchor", "end")
		.text(function(d,i) { return d; });
	
	
	svg.append("text")
		.attr("y", height+20)
		.attr("x", width/2 - 80)
		.style("text-anchor", "middle")
		.text("Frequency");
	
	svg.append("text")
		.attr("y", -75)
		.attr("x", -65)
		.attr("transform", "rotate(-90)")
		.style("text-anchor", "end")
		.text("Magnitude");
	
	var x = d3.scale.log()
		.rangeRound([20, 80, 140, 200, 260])
		.domain([.001, .01, .1, 1, 10]);

	var y = d3.scale.log()
		.rangeRound([10, 50, 90, 130, 170])
		.domain([10000000,1000000,100000,10000,1000]);
	
	var colors = ["blue", "red"];
	
	var index = [ "Very Low", "Low", "Medium", "High", "Very High"];
	var indexColors = [ "#339900", "#66FF00", "#FFFF00", "#FF9900", "#FF0000"];
	
	svg.selectAll("g").data(index).enter().append("rect")
		.attr("x", function(d, i){ return (gwidth * i) ; })
		.attr("y", height+ 45)
		.attr("height", 15)
		.attr("width", gwidth)
		.style("fill", function(d,i){ return indexColors[i]; });
	
	svg.selectAll("g").data(index).enter().append("text")
		.attr("x", function(d,i){ return (gwidth * i)+ (gwidth/2);})
		.attr("y", height + 42)
		.style("text-anchor", "middle")
		.style("font-size", 8)
		.text(function(d,i){ return d;});
	
	svg.append("text")
		.attr("x", -60)
		.attr("y", height + 55)
		.text("Legend");
	
	
	var defs = d3.select("#grid-container").select("svg").append("defs");
	var pattern = defs.append("pattern")
				.attr("id", "fillOne")
				.attr("x", 0)
				.attr("y", 0)
				.attr("width", 4)
				.attr("height", 4)
				.attr("patternUnits", "userSpaceOnUse");
	pattern.append("circle")
		.attr("cx", 2)
		.attr("cy", 2)
		.attr("r", 2)
		.style("stroke", "none")
		.style("fill", colors[0]);
	
	var pattern2 = defs.append("pattern")
	.attr("id", "fillTwo")
	.attr("x", 0)
	.attr("y", 0)
	.attr("width", 4)
	.attr("height", 4)
	.attr("patternUnits", "userSpaceOnUse");
		pattern2.append("circle")
		.attr("cx", 2)
		.attr("cy", 2)
		.attr("r", 2)
		.style("stroke", "none")
		.style("fill", colors[1]);
	
	var patterns = ["url(#fillOne)", "url(#fillTwo)"];
	
	for(var nn=0; nn<dd.length; nn++){
		
		
		var f = dd[nn].frequency;
		var m = dd[nn].magnitude;
		if(dd[nn].frequency < .001){ f = .001;}
		if(dd[nn].frequency > 10){ f = 10; }
		if(dd[nn].magnitude > 10000000){ m = 10000000;}
		if(dd[nn].magnitude < 1000){ m = 1000;}
		
		//alert(f + " and " + m);
		
		var xVal = Math.round(x(f)/gwidth) * gwidth;
		var yVal = (Math.round(y(m)/gheight)) * gheight;
		xVal += gwidth/2 - 10;
		yVal += gheight/2 - 10;
		
		d3.select("#grid-container").select("svg").append("rect")
		.attr("width", 20)
		.attr("height", 20)
		.attr("x", xVal)
		.attr("y", yVal)
		.style("fill", patterns[nn])
		.attr("transform", "translate(120, 20) rotate(45 " + (xVal+10) + ", " + (yVal+10) + ")");
	}
}


//buildMatrix({frequency: 11, magnitude: 999999}, {frequency: .001, magnitude: 25004000});