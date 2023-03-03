# Thepeer official Android SDK

![CI](https://github.com/thepeerstack/thepeer-android/actions/workflows/android.yml/badge.svg)

Thepeer Android SDK gives one integration access to all fintech businesses on your Android App


1. Send
3. Checkout
4. Direct Charge

## Demo

| Send        |  Checkout                  |  Direct Charge                         |
|------------------------ | --------------------------------------|--------------------------------------|
|![Send-shot](https://user-images.githubusercontent.com/16048595/221840767-aafc2abd-475f-4b2b-9c82-a7c2373cfd0e.png) | ![checkout-shot](https://user-images.githubusercontent.com/16048595/221840744-9efad3ca-8d5a-4186-91d4-7e9a3f408bf7.png)          | ![direct-charge-shot](https://user-images.githubusercontent.com/16048595/221840760-103db030-471d-4208-aa15-b2d97e2de96b.png)|



## Setup Implementation

#### Step 1: Add Dependency

To your root build.gradle file add:

```
allprojects {
    repositories {
        mavenCentral() 
    }
}
```

To your app-level build.gradle file add:

```groovy
dependencies {
    // ...
    implementation "co.thepeer:thepeer-android:[version]"
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

// initialize Thepeer SDK
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     

        // initialize Thepeer SDK
        val thepeer = Thepeer.Builder(
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

// initialize Thepeer SDK
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize Thepeer SDK
        Thepeer thepeer =new Thepeer.Builder(
                this,
                new BigDecimal("1000.00"),
                "NGN",
                getResources().getString(R.string.user_reference),
                new ThepeerResultListener())
                
                }
```

| Parameter name         |  Description                          |  Required                         |
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
        // Transaction Successful
        Log.v(TAG, transaction.toString())

    }

    override fun onError(error: Throwable) {
        // Transaction Error occured
        Log.e(TAG, error.message)
    }

    override fun onCancelled() {
        // Transaction was cancelled
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
 
