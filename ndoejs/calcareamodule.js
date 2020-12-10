var fs= require("fs");
function area(length,breadth){
	return parseInt(length) * parseInt(breadth);
}
function perimeter(length,breadth){
	return  2*(parseInt(length) + parseInt(breadth));
}	
exports.findword = function(word){
	var flag = true;
	/*fs.readFile("datafile.txt" ,function(err,data){
		 var pos = data.indexof(word);
		 console.log(pos);
			return pos;
		
	});*/
	var data=fs.readFileSync("datafile.txt");
	var pos=data.indexOf(word);
		 console.log(pos);
			return pos;
}
exports.area=area;
exports.perimeter=perimeter;