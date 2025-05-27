# Exemples de flux XML RSS25

Ce répertoire contient des exemples de flux XML conformes au format RSS25 qui peuvent être utilisés avec l'application RSS25SB.

## Fichiers d'exemples

1. **basic_feed_example.xml** - Un exemple minimal avec les éléments et attributs requis
2. **complex_feed_example.xml** - Un exemple complet avec plusieurs articles et tous les éléments optionnels
3. **scientific_feed_example.xml** - Un exemple spécialisé pour les publications scientifiques
4. **events_feed_example.xml** - Un exemple spécialisé pour les événements culturels

## Comment utiliser ces exemples

### Insertion via l'interface web

1. Accédez à la page d'insertion de flux : [http://localhost:5654/insert](http://localhost:5654/insert)
2. Copiez le contenu d'un des fichiers d'exemple
3. Collez-le dans le champ de texte du formulaire
4. Cliquez sur "Envoyer" pour insérer le flux

### Insertion via l'API REST

Vous pouvez également utiliser l'API REST directement :

```bash
curl -X POST -H "Content-Type: application/xml" -d @basic_feed_example.xml http://localhost:5654/rss25SB/insert
```

Remplacez `basic_feed_example.xml` par le chemin vers le fichier d'exemple que vous souhaitez utiliser.

## Structure du format RSS25

Chaque flux RSS25 doit contenir :

- Un élément racine `<feed>` avec l'espace de noms `http://univ.fr/rss25` et l'attribut `version="25"`
- Des métadonnées du flux : `<title>`, `<pubDate>`, `<copyright>`
- Au moins un lien `<link>` avec les attributs `rel`, `type` et `href`
- Au moins un article `<item>` (maximum 10)

Chaque article doit contenir :

- Un identifiant unique `<guid>`
- Un titre `<title>`
- Au moins une catégorie `<category>` avec l'attribut `term`
- Une date de publication `<published>` ou de mise à jour `<updated>`
- Un contenu `<content>` avec l'attribut `type`
- Au moins un auteur `<author>` ou contributeur `<contributor>`

## Personnalisation des exemples

Pour adapter ces exemples à vos besoins :

1. Modifiez les valeurs des éléments (titres, dates, contenus, etc.)
2. Assurez-vous que les GUIDs sont uniques (format UUID)
3. Respectez le format de date ISO 8601 (YYYY-MM-DDThh:mm:ssZ ou YYYY-MM-DDThh:mm:ss+hh:mm)
4. Vérifiez que le XML est bien formé et conforme au schéma RSS25

## Validation

Vous pouvez valider vos flux XML contre le schéma XSD situé dans `src/main/resources/schemas/rss25.tp1.xsd`.