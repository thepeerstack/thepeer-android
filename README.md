# ThePeer Android SDK

ThePeer Android SDK gives one integration access to all fintech businesses on your Android App


1. Send
3. Checkout
4. Direct Charge

## Demo

https://user-images.githubusercontent.com/16048595/167891393-6f198712-7648-4f45-959a-a289fab42006.mo

https://user-images.githubusercontent.com/16048595/167891381-2ad76193-3cf1-4abe-870b-0924fee498fb.mov 

https://user-images.githubusercontent.com/16048595/167891309-d6608bba-20dc-4840-be60-96c6ef390ea3.mov


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
    implementation "co.thepeer.android-sdk:android:sdk:[coming soon]"
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
| `currency `             | Currency which can be  `"NGN"` or  `"USD"`    |`true`|
| `userReference`         | The user reference returned by Thepeer API when a user has been indexed              |`true`|
| `meta`  | This object should contain additional/optional attributes you would like to have on your transaction response   |`false`|

## Send

Initiate the send request by calling the below function

KOTLIN

```kotlin

thePeer.send()

```

JAVA

```java

thePeer.send();

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

## Direct Charge

Initiate the Direct Charge request by calling the below function

KOTLIN

```kotlin

thePeer.directCharge()

```

JAVA

```java

thePeer.directCharge();

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
 
