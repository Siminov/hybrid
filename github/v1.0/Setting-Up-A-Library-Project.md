An library project is a development project that holds shared source code and resources. Other application projects can reference the library project and, at build time, include its compiled sources in their build files. Multiple application projects can reference the same library project and any single application project can reference multiple library projects.

Core provides mechanism to configure your library projects:

#### Android Setup

- In the **Package Explorer**, right-click the library project and select **Properties**.
- In the **Properties** window, select the Android properties group at left and locate the **Library** properties at right.
- Select the is Library check box and click **Apply**.
- Click **OK** to close the properties window.

![] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/setting_up_a_library_project.png "")


#### iOS Setup

- In Xcode, create a **Cocoa Touch Static Library project**.
- The new library project comes with a couple of template source files. You can delete these, and choose to “Move to Trash”.

![] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/setting_up_a_library_project.png "")

