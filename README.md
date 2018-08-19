# circlead.cloud

Circlead is a system which operates for an evolutionary role model. The is use for circular leadership for people and organisations. Circular leadership is a structural system (or software) and a set of principles based on the verse in the bible 1. peter 4,10.

This is a version written in java to create the role model logic on a minimal dataset. A dataset can be used as
* a cloud or dedicated version of atlassian confluence https://de.atlassian.com/software/confluence (or)
* a flat file repository of json-files.

This application synchronizes and merge all the data and write back simple html-files or confluence-pages. Two synchronizers are implemented:
* FileSynchronizer: Read json-files and writes back html-files
* AtlassianSynchronizer: Read Confluence Pages with datatables (over rest with json) and writes back rendered confluence-pages

![circlead-api](https://github.com/GebetshausFreiburg/circlead.cloud/blob/master/src/main/resources/circlead-api.jpg)

To understood the philosophy (and model) of circlead read the blog (only in german) at https://circleadonline.wordpress.com/.
