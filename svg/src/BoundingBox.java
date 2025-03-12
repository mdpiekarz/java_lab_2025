public record BoundingBox(double x, double y, double width, double height) {}

//Prostokątny obrys wielokąta (ang. bounding box) to najmniejszy prostokąt, który całkowicie otacza dany wielokąt.
// Można go sobie wyobrazić jako ramkę, która obejmuje wszystkie punkty wielokąta — nie wychodząc poza jego krawędzie
// bardziej, niż to konieczne.
//
//Dla dwuwymiarowej przestrzeni (jak w naszym przypadku) prostokątny obrys dla sceny svg określamy za pomocą:
//współrzędnych lewego górnego rogu: (minX, minY)
//szerokości: maxX - minX
//wysokości: maxY - minY