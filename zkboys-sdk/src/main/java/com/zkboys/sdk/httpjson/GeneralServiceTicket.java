package com.zkboys.sdk.httpjson;

import okhttp3.Call;

class GeneralServiceTicket implements ServiceTicket {

    private boolean canceled = false;
    private Call call;

    public GeneralServiceTicket() {
    }

    public GeneralServiceTicket(Call call) {
        this.call = call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void cancel() {
        if (this.call != null && (!this.call.isCanceled()) && (!this.call.isExecuted())) {
            this.call.cancel();
        }
        canceled = true;
    }
}