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

> Si le circuit est imparfait, on passe tous les sommets de ce circuit à "visited". Cela évite de repasser dessus par la suite.

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
			s.estVisite ← faux
			setAllVisited(s, visited)
		fsi
	fpour
fin
```
