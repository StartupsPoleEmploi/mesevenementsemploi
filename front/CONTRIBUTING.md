# Contribution

## Les issues

Les évolutions doivent passé par des branches créées depuis les issues.

Il existe 2 type d'issues :

- hotfix qui règlent des problèmes urgent en prod
- les autres, des évolutions, des fix, etc... qui sont à déployer sur la recette

### issues (hors hotfix)

Une issue est créée pour les évolutions ou les bugs mineurs. De cette issue une branche doit être créer depuis la branche develop pour pouvoir ensuite merger sur develop et déclencher la pipeline de build / déploiement sur le serveur de recette.

### hotfix

Les hotfix sont des issues qui sont des bugs majeurs à être déployer directement sur la preprod et ensuite mergés sur le prod et la recette.

Si une issue est un hotfix, il faut alors la lier à une branche dont le nom doit commener par 'feature/' et être créé depuis le master.
Lorsqu'une branche feature est poussé, alos une pipeline est créé pour déployer la branche en preprod.
Une fois le déploiement en preprod testé et validé alors il faut merger en prod et en recette, et publier.
