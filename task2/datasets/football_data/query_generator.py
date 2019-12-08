collection = "seriea"
pre = "db."+collection+".find()._addSpecial(\"$snapshot\",true).forEach(\n\tfunction(elem) {\n\t\tdb."+collection+".update(\n\t\t\t{_id: elem._id},\n\t\t\t{ $push: {\n"

post = "}});});"

bookmakers = ["bet365", "betwin", "Blue Square", "Gamebookers", "Interwetten", "Ladbrokes", "Pinnacle", "Sporting Odds", "Sportingbet", "Stan James", "Stanleybet", "VC Bet", "William Hill"]
codes = ["B365", "BW", "BS", "GB", "IW", "LB", "PS", "SO", "SB", "SJ", "SY", "VC", "WH"]
types = ["H", "D", "A", "OVER", "UNDER"] #tipologie di scommesse

typeLen = len(types)
len = len(bookmakers)

for j in range(0, typeLen):
	query = pre

	query += "\t\t\t\todds: { type:\"" + types[j] + "\", quotes: ["
	for i in range(0, len):
	    query = query + "{bookmaker: \"" + bookmakers[i] +"\", odd: elem." + codes[i]  + types[j] + "}"
	    if i < len-1: 
	    	query += ","

	query += " ] }\n"
	query = query + post
	print(query)
	print("")
