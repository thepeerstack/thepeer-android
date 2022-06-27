# Thepeer official Android SDK

Thepeer Android SDK gives one integration access to all fintech businesses on your Android App


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

//initialize Thepeer SDK
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     

        //initialize Thepeer SDK
        val thePeer = Thepeer.Builder(
            activity = this,
            amount = BigDecimal(10000.00),
            currency = "NGN",
            userReference = getString(R.string.user_reference),
            resultListener = resultListener
        ).setMeta(mapOf("remark" to "Enjoy")).build()
        
        }

```

JAVA

```java

//initialize Thepeer SDK
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize Thepeer SDK
        Thepeer thepeer =new Thepeer.Builder(
                this,
                new BigDecimal("1000.00"),
                "NGN",
                getResources().getString(R.string.user_reference),
                new ThepeerResultListener())
                
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

thepeer.send()

```

JAVA

```java

thepeer.send();

```

## Checkout 

Initiate the checkout request by calling the below function

KOTLIN

```kotlin

thepeer.checkout(email: String)

```

JAVA

```java

thepeer.checkout(String email);

```

## Direct Charge

Initiate the Direct Charge request by calling the below function

KOTLIN

```kotlin

thepeer.directCharge()

```

JAVA

```java

thepeer.directCharge();

```

## Listener

Once the request is initiated the SDK will wait from response from the service and notify the App
via `ThepeerResultListener`
KOTLIN

```Kotlin
private val resultListener = object : ThepeerResultListener {
    override fun onSuccess(transaction: ThepeerTransaction) {
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

 new ThepeerResultListener() {

                    @Override
                    public void onSuccess(@NonNull ThepeerTransaction transaction) {
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
## Support

If you're having trouble with Thepeer React or your integration, please reach out to us at <support@thepeer.co>. We're more than happy to help you out.

## Thepeer API References

- [Thepeer API Docs](https://docs.thepeer.co)
- [Thepeer Dashboard](https://dashboard.thepeer.co)
 
