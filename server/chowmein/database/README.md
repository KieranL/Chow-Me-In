# **Chow-Me-In Database**

Chow-Me-In uses AWS DynamoDB for it's data storage needs

The production database is hosted in AWS, but thanks to `DynamoDB Local` you can setup your own local database to use.

* [**Setup**](#setup)
    * [boto3 installation](#boto3-installation)
    * [aws cli installation](#aws-cli-installation)
    * [aws cli configuration](#aws-cli-configuration)
    * [DynamoDB Local installation](#dynamodb-local-installation)
    * [Putting it all together](#putting-it-all-together)
* [**Initializing your database**](#initializing-your-database)

## **SETUP**

Things you will need:
* `boto3`
* `aws cli`
* `DynamoDB Local`

### **boto3 installation**

You can install `boto3` with:

```
pip install boto3
```

or if you are on Mac:

```
pip install --ignore-installed six boto3
```

If you are having troubles installing this on Mac, you can look [here](https://github.com/boto/boto3/issues/296)

If that does not help, please send an email to _klass130@myumanitoba.ca_

### **aws cli installation**

You can install `aws cli` with:

```
pip install awscli
```

or 

```
brew install awscli
```

For more installation information, see [here](https://docs.aws.amazon.com/cli/latest/userguide/installing.html)

### **aws cli configuration**

Once you have `awscli` installed you need to configure your aws environment.

Run:

```
aws configure
```

You will be prompted to enter an Access Key, a Secret Access Key, a Region, and a output format

An example of a configuration for `DynamoDB Local`:

```
AWS Access Key Id: Some_Key
AWS Secret Access Key: Some_Secret_Key
Default region name: ca-central-1
Default output format: json
```

### **DynamoDB Local installation**

Download `DynamoDB Local` [here](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html#DynamoDBLocal.DownloadingAndRunning)

### **Putting it all together**

Once everything has been installed/downloaded, we can start running the server locally.

First, we need to get `DynamoDB Local` running.
To do so, use a separate console and navigate to where `DynamoDB Local` was downloaded, then run:

```
java -Djava.library.path=./DynamoDBLocal_lib/ -jar DynamoDBLocal.jar -sharedDb
```

## **Initializing your database**

After installation is complete, and you have `DynamoDB Local` running, you can choose to start from scratch or use our script to pre-populate your local database.

To do so, in the `server` folder run:
```
python dbinit.py
```