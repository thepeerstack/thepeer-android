# ThePeer Android SDK

ThePeer Android SDK gives one integration access to all fintech businesses on your Android App

1. Send Money
2. Checkout
3. Direct Debit

## Setup Implementation

#### Step 1: Add Dependency

To your root build.gradle file add:

```
allprojects {
    repositories {
        google()
        mavenCentral() 
    }
}
```

To your app-level build.gradle file add:

```groovy
dependencies {
    // ...
    implementation "co.thepeer.android-sdk:android:sdk:0.0.1-alpha"
}
```

#### Step 2: Add Public Key to Manifest

```
 <meta-data
    android:name="co.thepeer.PublicKey"
            android:value="YOUR_PUBLC_KEY" />
 ```

#### Step 3: Initialization

KOTLIN

```kotlin

//initialize ThePeer SDK
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     

        //initialize ThePeer SDK
        val thePeer = ThePeer.Builder(
            activity = this,
            amount = BigDecimal(10000.00),
            currency = ThePeerCurrency.NAIRA,
            userReference = getString(R.string.user_reference),
            resultListener = resultListener
        ).setMeta(mapOf("remark" to "Enjoy")).build()
        
        }

```

JAVA

```java

//initialize ThePeer SDK
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize ThePeer SDK
        ThePeer thePeer =new ThePeer.Builder(
                this,
                new BigDecimal("1000.00"),
                ThePeerCurrency.NAIRA,
                getResources().getString(R.string.user_reference),
                new ThePeerResultListener())
                
                }
```

| Paramater name          |  Description                          |  Required                         |
|------------------------ | --------------------------------------|--------------------------------------|
| `amount`                | The amount you intend to send and must be pass as an integer in kobo      |`true`|
| `currency `             | Currency which can be  `ThePeerCurrency.NGN` or  `ThePeerCurrency.USD`    |`true`|
| `userReference`         | The user reference returned by Thepeer API when a user has been indexed              |`true`|
| `meta`  | This object should contain additional/optional attributes you would like to have on your transaction response   |`false`|

## Send Money

Initiate the send money request by calling the below function

KOTLIN

```kotlin

thePeer.sendMoney()

```

JAVA

```java

thePeer.sendMoney();

```

## Checkout 

Initiate the checkout request by calling the below function

KOTLIN

```kotlin

thePeer.checkout(email: String)

```

JAVA

```java

thePeer.checkout(String email);

```

## Direct Debit

Initiate the Direct Debit request by calling the below function

KOTLIN

```kotlin

thePeer.directDebit()

```

JAVA

```java

thePeer.directDebit();

```

## Listener

Once the request is initiated the SDK will wait from response from the service and notify the App
via `ThePeerResultListener`
KOTLIN

```Kotlin
private val resultListener = object : ThePeerResultListener {
    override fun onSuccess(transaction: ThePeerTransaction) {
        //Transaction Successful
        Log.v(TAG, transaction.toString())

    }

    override fun onError(error: Throwable) {
        //Transaction Error occured
        Log.e(TAG, error.message)
    }

    override fun onCancelled() {
        //Transaction was cancelled
        Log.e(TAG, " Cancelled")
    }

}

```

JAVA

```java

 new ThePeerResultListener() {

                    @Override
                    public void onSuccess(@NonNull ThePeerTransaction transaction) {
                        ((TextView) findViewById(R.id.resultText)).setText(transaction.toString());
                    }

                    @Override
                    public void onCancelled() {

                    }

                    @Override
                    public void onError(@NonNull Throwable error) {

                    }

                }


```
 
