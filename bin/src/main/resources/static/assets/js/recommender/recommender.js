/**
 * 
 */

const ContentBasedRecommender = require("content-based-recommender");
const jsrecommender = require("js-recommender");

const recommender = new ContentBasedRecommender({
	minScore: 0.1,
	maxSimilarDocuments: 100,
});



const products = [
	{
		id: 1,
		content: "Giày thể thao chuyên dụng"
	}, {
		id: 2,
		content: "Giày da cao cấp"
	}, {
		id: 3,
		content: "Áo khoác hoodie"
	}, {
		id: 4,
		content: "Quần âu cao cấp"
	}, {
		id: 5,
		content: "Mũ lưỡi trai"
	}, {
		id: 6,
		content: "Quần jean dáng suông"
	}, {
		id: 7,
		content: "Áo sơ mi trắng"
	}, {
		id: 8,
		content: "Áo thun trắng"
	}, {
		id: 9,
		content: "Áo sơ mi đen"
	}, {
		id: 10,
		content: "Quần khaki nâu"
	}
];

recommender.train(products);

function getRecommendProducts(productId) {
	let similarDocuments = recommender.getSimilarDocuments(productId, 0, 10);
	console.log(similarDocuments);
}





const similarDocuments = recommender.getSimilarDocuments('1000002', 0, 10);

console.log(similarDocuments);










const ratingRecommender = new jsrecommender.Recommender();

let table = new jsrecommender.Table();

table.setCell('Love at last', 'Alice', 5);
table.setCell('Remance forever', 'Alice', 5);
table.setCell('Nonstop car chases', 'Alice', 0);
table.setCell('Sword vs. karate', 'Alice', 0);
table.setCell('Love at last', 'Bob', 5);
table.setCell('Cute puppies of love', 'Bob', 4);
table.setCell('Nonstop car chases', 'Bob', 0);
table.setCell('Sword vs. karate', 'Bob', 0);
table.setCell('Love at last', 'Carol', 0);
table.setCell('Cute puppies of love', 'Carol', 0);
table.setCell('Nonstop car chases', 'Carol', 5);
table.setCell('Sword vs. karate', 'Carol', 5);
table.setCell('Love at last', 'Dave', 0);
table.setCell('Remance forever', 'Dave', 0);
table.setCell('Nonstop car chases', 'Dave', 4);




let model = ratingRecommender.fit(table);
console.log(model);

let predicted_table = ratingRecommender.transform(table);
console.log(predicted_table);



/*for (var i = 0; i < predicted_table.columnNames.length; ++i) {
	var user = predicted_table.columnNames[i];
	console.log('For user: ' + user);
	for (var j = 0; j < predicted_table.rowNames.length; ++j) {
		var movie = predicted_table.rowNames[j];
		console.log('Movie [' + movie + '] has actual rating of ' + Math.round(table.getCell(movie, user)));
		console.log('Movie [' + movie + '] is predicted to have rating ' + Math.round(predicted_table.getCell(movie, user)));
	}
}*/







