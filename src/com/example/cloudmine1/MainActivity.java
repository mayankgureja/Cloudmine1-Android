package com.example.cloudmine1;

import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.SimpleCMObject;
import com.cloudmine.api.rest.CMStore;
import com.cloudmine.api.rest.callbacks.CMObjectResponseCallback;
import com.cloudmine.api.rest.callbacks.LoginResponseCallback;
import com.cloudmine.api.rest.callbacks.ObjectModificationResponseCallback;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.LoginResponse;
import com.cloudmine.api.rest.response.ObjectModificationResponse;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity
{
	// Find this in your developer console
	private static final String APP_ID = "2da9e5a53b654c40821695e1698631be";
	// Find this in your developer console
	private static final String API_KEY = "";
	private static final String readOnlyKey = "ac26896992834c1db471c94f95726908";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializeCredentials();

		// // Testing portion
		// create();
		// search();
		// getAll();
		// update();
		// delete();

		// Proof-of-concept portion
		// getAll();
		logInUser();
		// getAll();
		// create("ecec", "433", "Network Programming");
		update("cs", "433", "Networks", "ecec", "433", "Network Programming");
		getAll();

	}

	public void create(String dept, String number, String title)
	{
		// Add objects

		SimpleCMObject o = new SimpleCMObject();
		o.add("dept", dept);
		o.add("number", number);
		o.add("title", title);
		o.save(new ObjectModificationResponseCallback()
		{
			public void onCompletion(ObjectModificationResponse response)
			{
				System.out.println("course was saved: " + response.wasSuccess());
			}
		});
	}

	public void search()
	{
		// Search for a specific object
		String searchQuery = "[number = \"275\", dept=\"cs\"]";

		// use the default store for the application
		CMStore store = CMStore.getStore();

		store.loadApplicationObjectsSearch(searchQuery,
				new CMObjectResponseCallback()
				{
					public void onCompletion(CMObjectResponse response)
					{
						for (CMObject object : response.getObjects())
						{
							// only cs275 courses are returned
							SimpleCMObject course = (SimpleCMObject) object;
							System.out.println("Found course: "
									+ course.getString("title"));
						}
					}
				});
	}

	public void getAll()
	{
		// Search for all objects

		CMStore store = CMStore.getStore();
		store.loadAllApplicationObjects(new CMObjectResponseCallback()
		{
			public void onCompletion(CMObjectResponse response)
			{
				for (CMObject object : response.getObjects())
				{
					SimpleCMObject course = (SimpleCMObject) object;
					System.out.println("Retrieved course: "
							+ course.getString("title") + ": "
							+ course.getString("number"));
				}
			}
		});
	}

	public void update(String searchDept, String searchNumber,
			String searchTitle, final String updateDept,
			final String updateNumber, final String updateTitle)
	{
		// Update an object
		CMStore store = CMStore.getStore();
		String searchQuery = "[dept=\"" + searchDept + "\", number = \""
				+ searchNumber + "\", title = \"" + searchTitle + "\"]";

		store.loadApplicationObjectsSearch(searchQuery,
				new CMObjectResponseCallback()
				{
					public void onCompletion(CMObjectResponse response)
					{
						for (CMObject object : response.getObjects())
						{
							SimpleCMObject course = (SimpleCMObject) object;
							System.out.println("Found course: "
									+ course.getString("title"));

							final String objectId = course.getObjectId();
							course.add("dept", updateDept);
							course.add("title", updateTitle);
							course.add("number", updateNumber);
							course.save(new ObjectModificationResponseCallback()
							{
								public void onCompletion(
										ObjectModificationResponse response)
								{
									System.out
											.println("Response was a success? "
													+ response.wasSuccess());
									System.out.println("We: "
											+ response.getKeyResponse(objectId)
											+ " the simple object");
								}

								public void onFailure(Throwable e, String msg)
								{
									System.out.println("We failed: "
											+ e.getMessage());
								}
							});
						}
					}

					public void onFailure(Throwable e, String msg)
					{
						System.out.println("We failed: " + e.getMessage());
					}
				});
	}

	public void delete()
	{
		// Delete object

		CMStore store = CMStore.getStore();
		String searchQuery = "[number = \"275\", dept=\"cs\"]";

		store.loadApplicationObjectsSearch(searchQuery,
				new CMObjectResponseCallback()
				{
					public void onCompletion(CMObjectResponse response)
					{
						for (CMObject object : response.getObjects())
						{
							// only cs275 courses are returned
							SimpleCMObject course = (SimpleCMObject) object;
							System.out.println("retrieved course: "
									+ course.getString("title"));
							// object is a CMObject that has already been saved
							object.delete(new ObjectModificationResponseCallback()
							{
								public void onCompletion(
										ObjectModificationResponse response)
								{
									System.out.println("Response was: "
											+ response.wasSuccess());
								}

								public void onFailure(Throwable e, String msg)
								{
									System.out.println("We failed: "
											+ e.getMessage());
								}
							});
						}
					}
				});
	}

	public void initializeCredentials()
	{
		// This will initialize your credentials
		// CMApiCredentials.initialize(APP_ID, API_KEY,
		// getApplicationContext());
		CMApiCredentials.initialize(APP_ID, readOnlyKey,
				getApplicationContext());
	}

	public void logInUser()
	{
		// instantiate a CMUser instance with an email and password (presumably
		// from the UI)
		final CMUser user = new CMUser("test@test.com", "test");
		user.login(new LoginResponseCallback()
		{
			public void onCompletion(LoginResponse response)
			{
				if (response.wasSuccess())
				{
					System.out.println("User login was a success!");
					System.out.println("Now our user has a session token? "
							+ user.isLoggedIn());

					// configure the store with the user so that it can perform
					// user-centric operations
					CMStore.getStore().setUser(user);
				}
				else
				{
					System.out.println("We failed to log in because of: "
							+ response.getResponseCode());
				}
			}

			public void onFailure(Throwable t, String msg)
			{
				System.out
						.println("The call was never made because of: " + msg);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
