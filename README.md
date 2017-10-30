# xOWL Eclipse Satellite #

This repository contains the Eclipse plugins that can be used for the usage and development of a client access to the [xOWL Collaboration Platform](https://bitbucket.org/cenotelie/xowl-platform).


## How do I use this software? ##

The updates sites are deployed to:

```
https://nexus.cenotelie.fr/repository/raw/p2/xowl-satellite/${version}
```


## How to build ##

To build the artifacts in this repository using Maven:

```
$ mvn clean install -Dgpg.skip=true
```


## License ##

This software is licenced under the Lesser General Public License (LGPL) v3.
Refers to the `LICENSE.txt` file at the root of the repository for the full text, or to [the online version](http://www.gnu.org/licenses/lgpl-3.0.html).