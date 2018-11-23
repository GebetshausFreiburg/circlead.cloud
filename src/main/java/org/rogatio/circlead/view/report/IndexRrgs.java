/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view.report;

import java.io.File;
import java.io.IOException;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.model.data.WorkitemBreakdownElement;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * The Class IndexRrgs.
 */
public class IndexRrgs extends DefaultReport {
	
	/**
	 * Instantiates a new index rrgs.
	 */
	public IndexRrgs() {
		this.setName("Index RRGS");
		this.setDescription("Role Rolegroup Structure der Rollen");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.DefaultReport#render(org.rogatio.circlead.control.
	 * synchronizer.ISynchronizer)
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			WorkitemBreakdownElement rbs = Repository.getInstance().getRolesByRolegroupStructure(synchronizer);
			File f = new File("web" + File.separatorChar + "rolerolegroupstructure.json");
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();
			mapper.writeValue(f, rbs);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Element element = new Element("p");

		element.append("<a href=\"Index Circlead.html\">[Index]</a>");
		element.append("<br/>");

		// Sort-Algorithm see https://stackoverflow.com/questions/24336898/d3-bubble-chart-pack-layout-how-to-make-bubbles-radiate-out-from-the-largest
		element.append("<style>\n" + 
				".node {\n" + 
				"  cursor: pointer;\n" + 
				"}\n" + 
				"\n" + 
				".node:hover {\n" + 
				"  stroke: #FFFFFF;\n" + 
				"  stroke-width: 1.5px;\n" + 
				"}\n" + 
				"\n" + 
				".node--leaf {\n" + 
				"  fill: #d3d3d3;\n" + 
				"}\n" + 
				"\n" + 
				".label {\n" + 
				"  font: 10px \"Helvetica Neue\", Helvetica, Arial, sans-serif;\n" + 
				"  text-anchor: middle;\n" + 
				"  text-shadow: 0 1px 0 #fff, 1px 0 0 #fff, -1px 0 0 #fff, 0 -1px 0 #fff;\n" + 
				"}\n" + 
				"div.tooltip {	\n" + 
				"    position: absolute;			\n" + 
				"    text-align: center;			\n" + 
				"    width: 200px;					\n" + 
				"    height: 28px;					\n" + 
				"    padding: 2px;				\n" + 
				"    font: 12px sans-serif;		\n" + 
				"    background: white;	\n" + 
				"    border: 0px;		\n" + 
				"    border-radius: 8px;			\n" + 
				"    pointer-events: none;			\n" + 
				"}\n" + 
				"</style>\n" + 
				"<script src=\"javascript/d3/v4.0/d3.v3.min.js\"></script>\n" + 
				"<script>\n" + 
				"\n" + 
				"var restUrl = \"rolerolegroupstructure.json\";\n" + 
				"\n" + 
				"var margin = 20,\n" + 
				"    diameter = 550;\n" + 
				"\n" + 
				"var color = d3.scale.linear()\n" + 
				"    .domain([-1, 5])\n" + 
				"    .range([\"hsl(0,0%,20%)\", \"hsl(0,0%,80%)\"])\n" + 
				"    .interpolate(d3.interpolateHcl);\n" + 
				"\n" + 
				"var pack = d3.layout.pack().sort( function(a, b) {\n" + 
				"    var threshold = 1;\n" + 
				"    if ((a.size > threshold) && (b.size > threshold)) {\n" + 
				"        return -(a.size - b.size);\n" + 
				"    } else {\n" + 
				"        return -1;\n" + 
				"    }\n" + 
				"})\n" + 
				"    .padding(2)\n" + 
				"    .size([diameter - margin, diameter - margin])\n" + 
				"    .value(function(d) { return d.size; })\n" + 
				"\n" + 
				"var svg = d3.select(\"body\").append(\"svg\")\n" + 
				"    .attr(\"width\", diameter)\n" + 
				"    .attr(\"height\", diameter)\n" + 
				"  .append(\"g\")\n" + 
				"    .attr(\"transform\", \"translate(\" + diameter/2 +\n" + 
				"    \",\" + diameter/2 + \")\");\n" + 
				"\n" + 
				"d3.json(restUrl, function(error, root) {\n" + 
				"  if (error) throw error;\n" + 
				"\n" + 
				"  var focus = root,\n" + 
				"      nodes = pack.nodes(root),\n" + 
				"      view;\n" + 
				"\n" + 
				"  var circle = svg.selectAll(\"circle\")\n" + 
				"      .data(nodes)\n" + 
				"      .enter().append(\"circle\")\n" + 
				"        .on(\"mouseover\", function(d) {		\n" + 
				"            div.transition()		\n" + 
				"                .duration(200)		\n" + 
				"                .style(\"opacity\", .9);		\n" + 
				"            div	.html(d.name + \" (\"+d.category+\")\")	\n" + 
				"                .style(\"left\", (d3.event.pageX) + \"px\")		\n" + 
				"                .style(\"top\", (d3.event.pageY - 28) + \"px\");	\n" + 
				"            })					\n" + 
				"        .on(\"mouseout\", function(d) {		\n" + 
				"            div.transition()		\n" + 
				"                .duration(500)		\n" + 
				"                .style(\"opacity\", 0);	\n" + 
				"        })\n" + 
				"      .attr(\"class\", function(d) { return d.parent ? d.children ? \"node\" : \"node node--leaf\" : \"node node--root\"; })\n" + 
				"      .style(\"fill\", function(d) { return d.children ? color(d.depth) : d.color; })\n" + 
				"      .on(\"click\", function(d) { if (focus !== d) zoom(d), d3.event.stopPropagation(); })\n" + 
				"      .on(\"click\", clickFct)\n" + 
				"     function clickFct(d,i) {\n" + 
				"	  if (d3.select(this).classed(\"node--leaf\")) {\n" + 
				"    	  window.open(''+d.id+'.html', '_parent', '');\n" + 
				"       } else {\n" + 
				"          if (focus !== d) \n" + 
				"          {\n" + 
				"             zoom(d); \n" + 
				"             d3.event.stopPropagation();\n" + 
				"          }\n" + 
				"       }\n" + 
				"     };\n" + 
				"\n" + 
				"     var div = d3.select(\"body\").append(\"div\")	\n" + 
				"     .attr(\"class\", \"tooltip\")				\n" + 
				"     .style(\"opacity\", 0);\n" + 
				"     \n" + 
				"  var text = svg.selectAll(\"text\")\n" + 
				"      .data(nodes)\n" + 
				"    .enter().append(\"text\")\n" + 
				"      .attr(\"class\", \"label\")\n" + 
				"      .style(\"fill-opacity\", function(d) { return d.parent === root ? 1 : 0; })\n" + 
				"      .style(\"display\", function(d) { return d.parent === root ? \"inline\" : \"none\"; })\n" + 
				"      .text(function(d) { return \"\"; });\n" + 
				"\n" + 
				"  var node = svg.selectAll(\"circle,text\");\n" + 
				"\n" + 
				"  d3.select(\"body\")\n" + 
				"      .style(\"background\", \"#FFFFFF\")\n" + 
				"      .on(\"click\", function() { zoom(root); });\n" + 
				"\n" + 
				"  zoomTo([root.x, root.y, root.r * 2 + margin]);\n" + 
				"\n" + 
				"  function zoom(d) {\n" + 
				"    var focus0 = focus; focus = d;\n" + 
				"\n" + 
				"    var transition = d3.transition()\n" + 
				"        .duration(d3.event.altKey ? 7500 : 750)\n" + 
				"        .tween(\"zoom\", function(d) {\n" + 
				"          var i = d3.interpolateZoom(view, [focus.x, focus.y, focus.r * 2 + margin]);\n" + 
				"          return function(t) { zoomTo(i(t)); };\n" + 
				"        });\n" + 
				"\n" + 
				"    transition.selectAll(\"text\")\n" + 
				"      .filter(function(d) { return d.parent === focus || this.style.display === \"inline\"; })\n" + 
				"        .style(\"fill-opacity\", function(d) { return d.parent === focus ? 1 : 0; })\n" + 
				"        .each(\"start\", function(d) { if (d.parent === focus) this.style.display = \"inline\"; })\n" + 
				"        .each(\"end\", function(d) { if (d.parent !== focus) this.style.display = \"none\"; });\n" + 
				"  }\n" + 
				"\n" + 
				"  function zoomTo(v) {\n" + 
				"    var k = diameter / v[2]; view = v;\n" + 
				"    node.attr(\"transform\", function(d) { return \"translate(\" + (d.x - v[0]) * k + \",\" + (d.y - v[1]) * k + \")\"; });\n" + 
				"    circle.attr(\"r\", function(d) { return d.r * k; });\n" + 
				"  }\n" + 
				"});\n" + 
				"\n" + 
				"d3.select(self.frameElement).style(\"height\", diameter + \"px\");\n" + 
				"\n" + 
				"</script>");

		return element;
	}

}
