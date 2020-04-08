## COMPLEXITE - CONNECT
* Tristan Chaumont
* Léo Corrigeux
* Robin Prugne

> Projet visant à développer deux algorithmes (méthodes différentes) de détection du plus long cycle sur un plateau



![Projet Connect](https://i.imgur.com/I6TEHXW.png)



## Algorithmes utilisés

### Approche récursive

#### contientCycleUtil() 

> Méthode helper qui détecte s'il existe un cycle en partant d'un sommet.

```java
→ listeSommets: liste de tous les sommets du plateau
→ sommet: sommet à partir duquel on cherche un cycle
→ visited: tableau de booléens qui indique si un sommet a déjà été visité
→ parent: sommet parent de "sommet"
→ pre: liste des sommets déjà parcourus dans le cycle
← res: booléen qui indique si un cycle a été trouvé

debut
	// Si le sommet est une CROIX et qu'elle n'a pas 4 voisins, ce n'est pas un circuit parfait. On retourne false.
        // Si le sommet n'est pas une croix et qu'il n'a pas pas 2 voisins, ce n'est pas non plus un circuit parfait.
	si (sommet.type = CROIX et sommet.voisins.length < 4 ou sommet.voisins.length < 2) et sommet.type != BLANC faire
		setAllVisited(listeSommets, sommet, visited)
		réinitialisation de la liste pre
		res ← faux
	fsi
	
	// Marque le sommet courant comme visité
	visited[listeSommets.getIndex(sommet)] ← vrai
	sommet.estVisite ← vrai
	
	// Si le sommet est une CROIX alors, pour chacun de ses sommets adjacents, on essaie de trouver un cycle.
        // On ajoute le sommet adjacent dans la liste des prédécesseurs s'il n'a pas été visité (pour éviter les doublons).
        // On exécute l'algo de recherche d'un circuit à partir du sommet adjacent.
        // Si on ne trouve pas de circuit, c'est que le circuit n'est pas parfait.
	si sommet.type = CROIX faire
		nonVisites ← sommet.voisins qui n'ont pas encore été visités
		pour chaque sommet voisins dans nonVisites faire
			si voisin.estVisite = faux alors
				pre.ajouter(voisin)
				si contientCycleUtil(listeSommets, voisin, visited, sommet, pre) = faux alors
					res ← faux
				fsi
			fsi
		fpour
		res ← vrai
	fsi
	
	// Pour tous les sommets adjacents au sommet courant
	pour chaque sommet s dans sommet.voisins faire
		// Si le noeud adjacent n'a pas été visité, alors on réexécute la méthode sur ce nouveau sommet
            	si visited[listeSommets.getIndex(s)] = faux alors
			// Récurrence, on récupère la valeur du cycle
			value ← contientCycleUtil(listeSommets, s, visited, sommet, pre)
			
			si value = vrai alors
				 // On a déjà trouvé le cycle, donc on arrête l'algorithme
				 pre.ajouter(s)
				 res ← vrai
			fsi
			
		// Si le sommet adjacent a été visité et que ce n'est pas un parent du sommet actuel, alors il y a un cycle.
            	sinon si s != parent alors
			// On l'ajoute dans la liste des prédecesseurs
                	pre.ajouter(s)
                	res ← vrai
		fsi
	fpour
	res ← faux
fin
```

#### contientCycle() 

> Détecte tous les cycles du plateau

```java
→ listeSommets: liste de tous les sommets du plateau

debut
	visited ← tableau de booléens de taille listeSommets.length
	initialisation de la liste pre
	
	// Liste "circuits" qui contient tous les cycles trouvés dans le plateau
	initialisation de la liste circuits
	
	pour i de 0 à visited.length - 1 faire
		si visited[i] = faux alors
			si contientCycleUtil(listeSommets, listeSommets[i], visited, null, pre) = vrai alors
				circuits.ajouter(pre)
			fsi
		fsi
		réinitialisation de la liste pre
	fpour
fin
```

#### setAllVisited() 

> Si le circuit est imparfait, on passe tous les sommets de ce circuit à "visité". Cela évite de repasser dessus par la suite.

```java
→ listeSommets: liste de tous les sommets du plateau
→ sommet: sommet à partir duquel on cherche un cycle
→ visited: tableau de booléens qui indique si un sommet a déjà été visité

debut
	si visited[listeSommets.getIndex(sommet)] = faux alors
		visited[listeSommets.getIndex(sommet)] ← vrai
	fsi
	
	pour chaque sommet s dans sommet.voisins faire
		si visited[listeSommets.getIndex(sommet)] = faux alors
			s.estVisite ← vrai
			setAllVisited(s, visited)
		fsi
	fpour
fin
```

### Approche par le backtracking

#### backtrack()

> Détecte tous les cycles du plateau

```java
→ prec: case précédente visitée
→ i: index abscisse de déplacement de l''algorithme
→ j: index ordonnée de déplacement de l''algorithme
→ affect: booléen permettant de savoir si la case de départ viens d''être affectée ou non
← res: booléen qui indique si un cycle a été trouvé

debut
	estPasse ← faux

	//Si la case de départ n'est pas définie ou qu'elle correspond à une case vide
	si start = null ou start.getType() = Type.BLANC alors
		start ← cases[i][j]
		start.setCaseCompte()
		affect ← vrai
	fsi

	//si la case actuelle correspond à la case de départ et que l'on vient pas de l'affecter 
	si cases[i][j] = start et non affect et listeCases.size() >= 4 alors
		//si la case de départ n'est pas de type croix
	 	si start.getType() != Type.CROIX alors
	 		//on ajoute le cycle à la liste et on recommence
	 		listeCycle.add(listeCases)
	 		listes ← new ArrayList<Case>()
	 		start ← getFirstNonMarque()
	 		cases[i][j].setCaseCompte()

	 		ArrayList<Integer> indexes ← getIndexes(start)
				
			si backtrack(cases[i][j], indexes.get(0), indexes.get(1), vrai) alors
				retourne vrai
			fsi
		fsi
	fsi

	//on prend le nombre de connexions de la case actuelle
	ArrayList<Case> possibilites ← getNumConnexionsStrict(start, prec, i, j)
	//pour chaque connexion
	pour k de 0 à possibilites.size() faire
		si possibilites.get(k) != null alors				
			//on vérifie que si c'est une croix, tout les branches soit occupées
			ArrayList<Integer> ind ← getIndexes(possibilites.get(k))
			ArrayList<Case> possibilitesC1 ← getNumConnexions(start, cases[i][j], ind.get(0), ind.get(1))
			
			//differente possibilites pour ajouter la case suivante :
			//soit la case n'est pas comptée
			//soit la case correspond à la case de départ
			//soit la case est de type croix ET toutes ses branches sont complètes ET on est passé sur cette croix maximum 4 foix
			si non possibilites.get(k).estCompte()) ou (possibilites.get(k) = start) ou (possibilites.get(k).getType() = Type.CROIX et possibilitesC1.size() = 4 et getOccCase(possibilites.get(k)) <= 4) alors
				listeCases.add(possibilites.get(k))
				possibilites.get(k).setCaseCompte()
				estPasse ← vrai
				ArrayList<Integer> indexes ← getIndexes(possibilites.get(k))

				si backtrack(cases[i][j], indexes.get(0), indexes.get(1), faux) alors
					retourne vrai
				fsi
			fsi
		fsi
	fpour


	//si on n'est pas passé dans la boucle ci-dessus
	//on recommance avec une nouvelle case de départ, s'il ne reste plus de case, on arrête
	si non estPasse alors
		listeCases ← new ArrayList<Case>()
		start ← getFirstNonMarque()
		
		si start != null alors
			start.setCaseCompte()
			ArrayList<Integer> indexes ← getIndexes(start)
			
			si backtrack(cases[i][j], indexes.get(0), indexes.get(1), vrai) alors
				retourne vrai
			fsi
		fsi
	fsi
fin
```

#### getIndexes()

> Permet d'obtenir les index d'une case

```java
→ c: case donnée
← res: liste contenant les deux index, dans l''ordre abscisse et ordonnée

debut
	ArrayList<Integer> res ← new ArrayList<Integer>()
	res.add(c.getX())
	res.add(c.getY())
	retourne res
fin
```

#### getFirstNonMarque()

> Permet d'obtenir la première case du tableau qui n'a pas été marquée (sur laquelle on n'est jamais passé)

```java
← res: case correspondant à la premiere case du tableau non marquée

debut
	pour i de 0 à hauteur faire
		pour j de 0 à largeur faire
			si non cases[i][j].estCompte() alors
				retourne cases[i][j]
			fsi
		fpour
	fpour
		
	retourne null
fin
```

#### getNumConnexions()

> Permet d'obtenir le nombre de connexions de la case située en i, j

```java
→ prec: case précédente
→ start: case de départ
→ i: index abscisse
→ j: index ordonnée
← res: nombre de connexions de la case située en i, j

debut
	si start != null alors
		si (j < largeur-1) et cases[i][j+1] != null alors
			si contientCase(listeCycle, cases[i][j+1]) ou listeCases.contains(cases[i][j+1]) ou non cases[i][j+1].estCompte() ou cases[i][j+1] = prec alors
				res.add(cases[i][j+1])
			fsi
		fsi
		si (i < hauteur-1) et cases[i+1][j] != null alors
			si contientCase(listeCycle, cases[i+1][j]) ou listeCases.contains(cases[i+1][j]) ou non cases[i+1][j].estCompte() ou cases[i+1][j] = prec alors 
				res.add(cases[i+1][j])
			fsi
		fsi
		si (j > 0) et cases[i][j-1] != null alors
			si contientCase(listeCycle, cases[i][j-1]) ou listeCases.contains(cases[i][j-1]) ou non cases[i][j-1].estCompte() ou cases[i][j-1] = prec alors
				res.add(cases[i][j-1])
			fsi
		fsi
		si (i > 0) et cases[i-1][j] != null alors
			si contientCase(listeCycle, cases[i-1][j]) ou listeCases.contains(cases[i-1][j]) ou non cases[i-1][j].estCompte() ou cases[i-1][j] = prec alors
				res.add(cases[i-1][j])
			fsi
		fsi
	fsi
    	
	retoutne res
fin
```