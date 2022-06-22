package co.thepeer.sdk

object MockData {


    const val sendSuccess =
        "{\"event\":\"send.success\",\"data\":{\"id\":\"ea3a2ccb-ed0b-4bb6-8a7b-89a09a77cd62\",\"remark\":\"okk\",\"amount\":10000,\"charge\":0,\"refund\":false,\"type\":\"peer\",\"currency\":\"NGN\",\"status\":\"success\",\"mode\":\"debit\",\"meta\":{\"city\":\"Uyo\"},\"reference\":\"a5092442d5e3ddjjdjd\",\"checkout\":null,\"peer\":{\"business\":{\"name\":\"Cash App\",\"logo\":\"https://image.png\",\"logo_colour\":\"#77cc33\"},\"user\":{\"name\":\"user\",\"identifier\":\"sharp\",\"identifier_type\":\"username\"}},\"user\":{\"name\":\"user\",\"identifier\":\"doreen\",\"identifier_type\":\"username\",\"email\":\"user@gmail.com\",\"reference\":\"73f03de5-1043-4adhhfh\",\"created_at\":\"2021-04-19T19:50:26.000000Z\",\"updated_at\":\"2022-02-14T22:58:25.000000Z\"},\"channel\":\"send\",\"created_at\":\"2022-05-18T20:34:28.000000Z\",\"updated_at\":\"2022-05-18T20:34:28.000000Z\"}}"

    const val sendClose = "{\"event\":\"send.close\"}"
    const val sendError = "{\"event\":\"send.user_insufficient_funds\"}"

    const val checkoutSuccess =
        "{\"event\":\"checkout.success\",\"data\":{\"id\":\"ea3a2ccb-ed0b-4bb6-8a7b-89a09a77cd62\",\"remark\":\"okk\",\"amount\":10000,\"charge\":0,\"refund\":false,\"type\":\"peer\",\"currency\":\"NGN\",\"status\":\"success\",\"mode\":\"debit\",\"meta\":{\"city\":\"Uyo\"},\"reference\":\"a5092442d5e3ddjjdjd\",\"checkout\":null,\"peer\":{\"business\":{\"name\":\"Cash App\",\"logo\":\"https://image.png\",\"logo_colour\":\"#77cc33\"},\"user\":{\"name\":\"user\",\"identifier\":\"sharp\",\"identifier_type\":\"username\"}},\"user\":{\"name\":\"user\",\"identifier\":\"doreen\",\"identifier_type\":\"username\",\"email\":\"user@gmail.com\",\"reference\":\"73f03de5-1043-4adhhfh\",\"created_at\":\"2021-04-19T19:50:26.000000Z\",\"updated_at\":\"2022-02-14T22:58:25.000000Z\"},\"channel\":\"send\",\"created_at\":\"2022-05-18T20:34:28.000000Z\",\"updated_at\":\"2022-05-18T20:34:28.000000Z\"}}"

    const val checkoutClose = "{\"event\":\"checkout.close\"}"

    const val directDebitSuccess =
        "{\"event\":\"direct_debit.success\",\"data\":{\"id\":\"ea3a2ccb-ed0b-4bb6-8a7b-89a09a77cd62\",\"remark\":\"okk\",\"amount\":10000,\"charge\":0,\"refund\":false,\"type\":\"peer\",\"currency\":\"NGN\",\"status\":\"success\",\"mode\":\"debit\",\"meta\":{\"city\":\"Uyo\"},\"reference\":\"a5092442d5e3ddjjdjd\",\"checkout\":null,\"peer\":{\"business\":{\"name\":\"Cash App\",\"logo\":\"https://image.png\",\"logo_colour\":\"#77cc33\"},\"user\":{\"name\":\"user\",\"identifier\":\"sharp\",\"identifier_type\":\"username\"}},\"user\":{\"name\":\"user\",\"identifier\":\"doreen\",\"identifier_type\":\"username\",\"email\":\"user@gmail.com\",\"reference\":\"73f03de5-1043-4adhhfh\",\"created_at\":\"2021-04-19T19:50:26.000000Z\",\"updated_at\":\"2022-02-14T22:58:25.000000Z\"},\"channel\":\"send\",\"created_at\":\"2022-05-18T20:34:28.000000Z\",\"updated_at\":\"2022-05-18T20:34:28.000000Z\"}}"

    const val directDebitClose = "{\"event\":\"direct_debit.close\"}"
}