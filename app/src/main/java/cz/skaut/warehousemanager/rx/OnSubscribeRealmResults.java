package cz.skaut.warehousemanager.rx;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;

public abstract class OnSubscribeRealmResults<T extends RealmObject> implements Observable.OnSubscribe<RealmResults<T>> {
	private final Context context;

	public OnSubscribeRealmResults(Context context) {
		this.context = context.getApplicationContext();
	}

	@Override
	public void call(final Subscriber<? super RealmResults<T>> subscriber) {
		final Realm realm = Realm.getDefaultInstance();
		subscriber.add(Subscriptions.create(() -> {
			try {
				realm.close();
			} catch (RealmException ex) {
				subscriber.onError(ex);
			}
		}));

		RealmResults<T> object;
		realm.beginTransaction();
		try {
			object = get(realm);
			realm.commitTransaction();
		} catch (RuntimeException e) {
			realm.cancelTransaction();
			subscriber.onError(new RealmException("Error during transaction.", e));
			return;
		} catch (Error e) {
			realm.cancelTransaction();
			subscriber.onError(e);
			return;
		}
		if (object != null) {
			subscriber.onNext(object);
		}
		subscriber.onCompleted();
	}

	public abstract RealmResults<T> get(Realm realm);
}