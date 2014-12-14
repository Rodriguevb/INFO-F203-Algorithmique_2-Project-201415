int x_real = 0;
int y_real = 0;
for( int x = 1; x < matrice.length-1; x+=2 ) {
	for( int y = 2; y < matrice[0].length-2; y+=4 ) {
		Case newCase = new Case();
		newCase.top   = matrice[x][y+2];
		newCase.right = matrice[x+1][y];
		newCase.down  = matrice[x][y-2];
		newCase.left  = matrice[x-1][y];
		_labyrinthe.set( x_real, y_real, newCase);
		y_real += 1;
	}
	x_real += 1;
	y_real = 0;
}