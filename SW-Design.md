# MyWallet - SW Design
## Global Architecture

Following the requirements, the App shall function with or without internet connection and must provide a mean to synchronize data between a distant database and a local copy.
To allow this, we consider using a block of code used by the server and the app insuring that the both are handled identically.
*Note: Open Question: if we do this we impose Java or Kotling for both server and apps.*
Therefore, the structure of project should be:
>MyWallet
>
>   |----- AppCommon
>   
>   |----- MyWalletServer
>   
>   |----- MyWalletApp

Each of the above projects are git repositories linked into a master one "MyWallet".
Dependency graph should be:

``` mermaid
classDiagram
    MyWalletServer --> AppCommon
    MyWalletApp --> AppCommon
```

### AppCommon package responsibilities
- Provide interfaces to consume commands towards the Database

### MyWalletServer responsibilities
Receives connection of apps and processes commands to put into database.
In addition, it pushes to apps the updated datas.

### MyWalletApp responsibilities
Android App to control all content over its accounts.

## MyWalletApp Architecture
To perform all of its tasks successfully, the App will be using Android's ViewModel due to their persistancy.
Each ViewModel will have access to:
- Data-Write object (Sync): either cache the changes locally into the DB and, if server is reachable, push them directly to the Server.
- Data-Read object (DataAccess): reads local database and provides all the reads methods to fetch content from the database.

*Note: Sync and DataAccess may be declined in different objects to respect the SOLID design approach.
This diagram only provides an idea of the different layers we migth have.*

``` mermaid
classDiagram
    Activity1 --> ViewModel1
    Activity2 --> ViewModel1
    Activity3 --> ViewModel2
    ViewModel1 --> Sync
    ViewModel1 --> DataAccess
    ViewModel2 --> Sync
    ViewModel2 --> DataAccess
    Sync --> NetSync
    Sync --> LocalSync
    NetSync --> gRPC instance
    LocalSync --> SQLiteDB
    DataAccess --> SQLiteDB
```

OR directly:
https://firebase.google.com/docs/database
Performs perfectly the server job. => No Server left at all simply a firebase realtime database.


## MyWalletServer Architecture

## Com interface
Proposition of use of the following middleware:
https://developer.android.com/guide/topics/connectivity/grpc
https://developer.android.com/training/sync-adapters

Messages shall be containing the "Commands" used to push into the database.
What we do not want: have the database server in direct access.
