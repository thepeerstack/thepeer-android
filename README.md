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
    val thepeer = Thepeer.Initiate(
        activity = this,
        userReference = "YOUR _USER_REFERENCE",
        resultListener = resultListener).build()
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
        Thepeer thepeer = new Thepeer.Initiate(
                getResources().getString(R.string.user_reference),
                activity,
                new ThepeerResultListener())
                
                }
                
```
| Parameter name         |  Description                          |  Required                         |
|------------------------ | --------------------------------------|--------------------------------------|
| `userReference`         | The user reference returned by Thepeer API when a user has been indexed              |`true`|

## Configuration
Every request will require this configuration to initiate a transaction.

```
  val config = ThepeerConfig(amount = BigDecimal(100000), currency = "NGN", meta  = mapOf())
  
```
| Parameter name         |  Description                          |  Required                         |
|------------------------ | --------------------------------------|--------------------------------------|
| `amount`                | The amount you intend to send and must be pass as an integer in kobo      |`true`|
| `currency `             | Currency which can be  `"NGN"` or  `"USD"`    |`true`|
| `meta`  | This object should contain additional/optional attributes you would like to have on your transaction response   |`false`|

## Send

Initiate the send request by calling the below function

KOTLIN

```kotlin
 val config = ThepeerConfig(amount = BigDecimal(100000), currency = "NGN", meta  = mapOf())
thepeer.send(config = config)

```

JAVA

```java

thepeer.send(config);

```

## Checkout 

Initiate the checkout request by calling the below function

KOTLIN

```kotlin

val config = ThepeerConfig(amount = BigDecimal(100000), currency = "NGN", meta  = mapOf())
thepeer.checkout("email@gmail.com", config = config)

```

JAVA

```java

thepeer.checkout("email@gmail.com", config);

```

## Direct Charge

Initiate the Direct Charge request by calling the below function

KOTLIN

```kotlin
 val config = ThepeerConfig(amount = BigDecimal(100000), currency = "NGN", meta  = mapOf())
thepeer.directCharge(config)

```

JAVA

```java

thepeer.directCharge(config);

```

## Listener

Once the request is initiated the SDK will wait from response from the service and notify the App
via `ThepeerResultListener`
KOTLIN

```Kotlin
private val resultListener = object : ThepeerResultListener {
    override fun onSuccess(response: String) {
        // Transaction Successful
        Log.v(TAG,response)

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
                    public void onSuccess(@NonNull String transaction) {
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
## Response structure
```JSON
{
  "event": "send.success",
  "type": "send.success",
  "data": {
    "id": "744bdf8f-17a6-46ae-bda1-b348c6d22f89",
    "amount": 100000,
    "channel": "send",
    "refund": false,
    "checkout": null,
    "user": {
      "reference": "73f03de5-1043-4ad1-bc2e-aa4d94ebee4f",
      "name": "Doreen Okoh",
      "identifier": "doreen",
      "identifier_type": "username",
      "email": "doreen@okoh.co.uk",
      "created_at": "2021-04-19T19:50:26.000000Z",
      "updated_at": "2022-02-14T22:58:25.000000Z"
    },
    "charge": 1000,
    "currency": "NGN",
    "mode": "debit",
    "reference": "d34dfaebd727e40a8f436a4b43acbf73",
    "remark": "food",
    "status": "success",
    "type": "peer",
    "meta": null,
    "peer": {
      "business": {
        "name": "Cash App",
        "logo": "https://palaciodepeer.s3.us-east-2.amazonaws.com/business_logos/UJimBqYOu7KQIM3DwCWOuKjkDbBbVLYRuYRTgxKh.png",
        "logo_colour": "#77cc33"
      },
      "user": {
        "name": "Trojan Okoh",
        "identifier": "trojan",
        "identifier_type": "username"
      }
    },
    "updated_at": "2023-05-25T12:32:03.000000Z",
    "created_at": "2023-05-25T12:32:03.000000Z"
  }
}
```
## Support

If you're having trouble with Thepeer React or your integration, please reach out to us at <support@thepeer.co>. We're more than happy to help you out.

## Thepeer API References

- [Thepeer API Docs](https://docs.thepeer.co)
- [Thepeer Dashboard](https://dashboard.thepeer.co)
 
