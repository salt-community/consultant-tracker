# Consultant Tracker

Application created to help core team keep track of consultants.
Provides visual (gantt chart) representation of worked time,
absences, vacations and remaining time for each consultant.

### Technologies - frontend

TypeScript, React, Vite, MUI, Redux toolkit, npm

### Starting frontend

- node version v20.15.0
- create .env.local file and add secrets:
```
  VITE_CLERK_PUBLISHABLE_KEY=
  VITE_BACKEND_URL=
```
- substitute secrets on gitHub with yours
```
Settings ⟶ Security ⟶ Secrets and variables ⟶ Actions
```
- to start project in dev mode you need to enter 
 /frontend folder and run commands:
```
npm i
npm run dev
```

- when pushing new changes run 
```
npm run build
```

### Deployment of frontend

- deployment file to GoogleCloud is inside workflows build-and-deploy-to-gcp.yml 
(we left also other successful build files we tried as a reference)
- to see deployed frontend you need to enter: [consultant-tracker](https://consultant-tracker-client-735865474111.europe-north1.run.app)
Keep in mind: to access deployed version of consultant-tracker you need to have email 
**@appliedtechnology.se** and be on the **USER_EMAIL** or **ADMIN_EMAIL** list (to add yourself you need to follow second path)
on GoogleCloud:
```
1. pgp-sandbox ⟶ CloudRun ⟶ consultant-tracker-client (region: europe-north-1)

2. pgp-sandbox ⟶ CloudRun ⟶ consultant-tracker-server (region: europe-north-1) 
⟶ Edit & Deploy New Revision ⟶ VARIABLES & SECRETS ⟶ USER_EMAILS || ADMIN_EMAILS

```

#### Folder Structure Frontend
```bash
├───assets
├───components
│   ├───accordion
│   ├───authentication
│   ├───card-details
│   │   └───tabs
│   ├───consultant-detail
│   │   ├───absence-info
│   │   ├───basic-info
│   │   │   └───header
│   │   │       └───avatar
│   │   ├───client
│   │   ├───personal-data
│   │   └───schedule
│   ├───dashboard
│   │   └───dashboard-header
│   │       └───infographic
│   ├───error
│   ├───filter
│   │   └───multiselect
│   ├───gantt-chart
│   │   └───legend
│   │       └───item-color
│   ├───loading
│   ├───modal-db
│   │   └───progress-bar
│   ├───navbar
│   │   ├───logo
│   │   └───sign-out
│   ├───pagination
│   ├───single-detail-field
│   └───slices
├───utils
└───view
    ├───home
    ├───page-not-found
    ├───sign-in
    └───unauthorized

```
<details><summary>Folder details</summary>

> assets - includes images for error pages (404, 401, 403) </br>

> accordion - component used for legend above gantt-chart

> authentication - component includes logic for Clerk authentication, sets user (needed for correct display of infographics) and provides jwt token. 
> Original token did not include email. In order to include it in Clerks dashboard/consultant-tracker/configure ⟶ 
> we created our own JWT template with lifetime of 900 seconds and additional email_address claim. 
> Name of our template is included in constants.ts. 
> Needs to be set and adjusted accordingly for new Clerks account.
> Token is sent to backend and grants access to user if email extracted from token is included in 
> secret variable 'PT_EMAILS'.

> card-details - component implemented from MUI, appears below gantt-chart when clicking consultants name or time item. 
> Currently displays 4 tabs that are defined in constants.ts (can be expanded if needed).

> dashboard - main component that includes all other parts of the page. 
> Includes infographics component that represents numerical statistics for PT. 
> If logged in person does not have any consultants, only two infographics are shown.

> error - component used for displaying errors.

> filter - component visible between infographics and gantt chart. 
> Offers 3 different options to filter: by consultants name, multiple responsiblePTs and multiple clients. 
> Option to clear filters and include people in PGP. Data for PGP on gantt chart are not as accurate 
> due to registering only certain activity.

> gantt-chart - component based on react-calendar-timeline library(lack of different alternatives) 
> library is not supported anymore therefore while installing dependencies might appear alert about vulnerabilities.
> Full documentation regarding library can be found [here](https://github.com/namespace-ee/react-calendar-timeline).


> consultant-details - component is connected to card-details as it holds content of the tabs.

> lotties - includes json cat animation implemented in <Loading /> component.
> Instruction how we implemented it can be found
> [here](https://lottiefiles.com/blog/working-with-lottie-animations/how-to-use-lottie-in-react-app/)


</details>






### Future features

- scheduled meetings notification system
- custom color picker for gantt-chart from legend level
- integration with Lucca




