# $\color{rgb(60,143,201)}{\textsf{Consultant Tracker}}$

Application created to help core team keep track of consultants.
Provides visual (gantt chart) representation of worked time,
absences, vacations and remaining time for each consultant.


## $\color{rgb(107, 187, 242)}{\textsf{Technologies - frontend}}$

TypeScript, React, Vite, MUI, Redux toolkit, npm


## $\color{rgb(107, 187, 242)}{\textsf{Starting frontend}}$

- node version v20.15.0


> [!NOTE]
> It is important to set the JWT to accept emails
> Clerk ⟶ consultant-tracker project ⟶ Configure ⟶ Sessions management ⟶ JWT templates ⟶ New template ⟶ Blank ⟶ name it (consultant-tracker-email) ⟶ in the claims enter the following
```
  {
    "email_address": "{{user.primary_email_address}}"
  }  
```


> [!NOTE]
> If you are going to set up Clerk it is important to restrict only emails for @appliedtechnology.se
> In order to do that go to:
>```
>    1. Clerk ⟶ consultant-tracker project ⟶ Configure ⟶ User&Authentication ⟶ Social connections ⟶ Auth providers 
>   ⟶ click on cog next to Used for sign-in ⟶ enter Client ID and Client secret from GoogleCloud
>    a) pgp-sandbox ⟶ APIs & Services ⟶ Credentials ⟶ Consultant-Tracker ⟶ Client ID
>    b) pgp-sandbox ⟶ APIs & Services ⟶ Credentials ⟶ Consultant-Tracker ⟶ click on edit pencil ⟶ Client secret
>```

- in /frontend folder create .env.local file and add secrets (secrets can be provided by Salt admin). Check Notion Proxy URL on their [GitHub](https://github.com/salt-community/notion-proxy/):
```
  VITE_CLERK_PUBLISHABLE_KEY=
  VITE_BACKEND_URL=
  NEXT_PUBLIC_API_URL=http://localhost:8080
  VITE_BACKEND_URL=http://localhost:8080
  NOTION_PROXY_URL=
```

> [!TIP]
> VITE_CLERK_PUBLISHABLE_KEY - you can find it on your Clerk account:
>```
>    Clerk ⟶ consultant-tracker project ⟶ Configure ⟶ Developers ⟶ API keys
>```
> VITE_BACKEND_URL - you can copy from GoogleCloud 
> ```
> pgp-sandbox ⟶ CloudRun ⟶ consultant-tracker-server (region: europe-north-1)
> ```


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

- state is handled using redux-toolkit in order to see actual state in dev mode in the browser
install redux devtools

- before pushing new changes run 
```
npm run build
```


## $\color{rgb(107, 187, 242)}{\textsf{Deployment of frontend}}$

- deployment file to GoogleCloud is inside workflows build-and-deploy-to-gcp.yml and in frontend/src/nginx.conf
(we left also other successful build files we tried as a reference)

> [!TIP]
> If you will need to generate new GCP_SA_KEY you need to enter:
>```
>  pgp-sandbox ⟶ Service Accounts ⟶ select second service starting 735..
>   ⟶ KEYS ⟶ ADD KEY ⟶  place json on github in GCP_SA_KEY secret
>```

- to see deployed frontend you need to enter: [consultant-tracker](https://consultant-tracker-client-735865474111.europe-north1.run.app)

Keep in mind: to access deployed version of consultant-tracker you need to have email 
**@appliedtechnology.se** and be on the **USER_EMAIL** or **ADMIN_EMAIL** list (to add yourself you need to follow second path)
on GoogleCloud:
```
1. pgp-sandbox ⟶ CloudRun ⟶ consultant-tracker-client (region: europe-north-1)

2. pgp-sandbox ⟶ CloudRun ⟶ consultant-tracker-server (region: europe-north-1) 
⟶ Edit & Deploy New Revision ⟶ VARIABLES & SECRETS ⟶ USER_EMAILS || ADMIN_EMAILS

```

## $\color{rgb(107, 187, 242)}{\textsf{Folder Structure Frontend}}$
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
│   │   ├───legend
│   │   │   └───item-color
│   │   └───timeline-component
│   ├───loading
│   ├───modal-db
│   │   └───progress-bar
│   ├───navbar
│   │   ├───logo
│   │   └───sign-out
│   ├───pagination
│   ├───single-detail-field
│   ├───text-input
│   └───time-item-details
├───lotties
├───routing
├───store
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
> Original token did not include email. In order to include it in Clerk go to dashboard/consultant-tracker/configure ⟶ 
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
> Item renderers and group renderers have been implemented in order to customize style and functionality of elements.
> (Salt has currently 4 consultants from Norway) method responsible is used in utils.ts ⟶ verticalLineClassNamesForTime
> Gantt-chart has 4 different views monthly, weekly, daily, hourly. Change can be obtained by clicking two top 
> bars indicating period of time. 
> Weekly view shows additional vertical indicators for weekends and redDays (Sweden and Norway).

> loading - component displayed during fetching of data it implements animation from lotties(details below)

> modal-db - component created for admin button that populated db, it prevents 
> from accidental clicking and displays integral component progress bar.
> Modal cannot be exited until process is successful or ends with error.
> Designed that way to not interrupt with accidental clicking.

> navbar - contains salt logo originally application was supposed to have subpages 
> and click on logo was redirecting to home page. 
> Application was developed using next.js however we migrated to vite 
> and decided to make intuitive interface and easy to use for end user. 

> pagination - component displayed in order to show amount of records provides by default 5 
> results per page but offers also 10 and 25, can be easily adjusted by adding value of choice to rowsPerPageOptions.

> single-detail-field

> consultant-details - component is connected to card-details as it holds content of the tabs.

> lotties - includes json cat animation implemented in <Loading /> component.
> Instruction how we implemented it can be found
> [here](https://lottiefiles.com/blog/working-with-lottie-animations/how-to-use-lottie-in-react-app/)

> store - state is managed using redux toolkit. Keep in mind: in few cases related to 
> react-calendar-timeline we were forces to use type any. It should be adjusted in the future if possible. 
> It took really long time to try to fix it in order to build project. Unfortunately the resul was unsuccessful.

> utils- contains colors for gantt chart and mapping for groups and time items for gantt-chart as well as 
> previously mentioned method to distinct redDays and weekends.




</details>

##  $\color{rgb(107, 187, 242)}{\textsf{Technologies - backend}}$

Java, Spring Boot, Lombok, Spring Security, Maven, Docker

## $\color{rgb(107, 187, 242)}{\textsf{Starting backend}}$ 

- java version 21.0.4
- create secret.yml in backend/main/resources and add secrets (secrets can be provided by Salt admin):

```
DB:
  USERNAME: 
  PASSWORD: 
  URL: 
TIMEKEEPER:
  URL: 
  AUTH: 
NAGER:
  URL: 
NOTION_PROXY:
  URL: 
  AUTH: 
ISSUER_URI: 
JWT: 
USER_EMAILS: 
ADMIN_EMAILS:
```

> [!TIP]
> ISSUER_URI and JWT you can find  on your Clerk account:
>```
>    1.Clerk ⟶ consultant-tracker project ⟶ Configure ⟶ User&Authentication ⟶ Social connections 
>    ⟶ click on Google cog icon ⟶ copy base url without v1/oauth_callback
>    2.Clerk ⟶ consultant-tracker project ⟶ Configure ⟶ Session management ⟶ JWT templates ⟶ Issuer input
>```

- substitute secrets on GoogleCloud with yours:
```
 pgp-sandbox ⟶ CloudRun ⟶ consultant-tracker-server (region: europe-north-1)
   ⟶ Edit & Deploy New Revision ⟶ VARIABLES & SECRETS
```
- before you start application don't forget to run docker.
Enter /backend folder and run command:
```
 docker compose up
```
- to run application in demo mode select BackendApplication 
in the toolbar and Edit configuration. Type "demo" in Active profiles input and start

- before pushing new changes build application and make sure all tests are passing
```
    mvn clean package 
```

## $\color{rgb(107, 187, 242)}{\textsf{Deployment of backend}}$ 

- by default trigger is disabled, to run deployment or enable trigger
you need to go to:
```
 pgp-sandbox ⟶ CloudRun ⟶ consultant-tracker-server (region: europe-north-1)
   ⟶ Build History ⟶ Triggers ⟶ rmgpgab-consultant-tracker-server-europe-north1-salt-communiyqz ⟶ 
   RUN (if you want to deploy manually) or ENABLE to deploy with every push to main branch)
```
## $\color{rgb(107, 187, 242)}{\textsf{Folder Structure Backend}}$
```
├───java
│   └───salt
│       └───consultanttracker
│           └───api
│               ├───client
│               │   ├───nager
│               │   │   └───dto
│               │   ├───notion
│               │   │   └───dtos
│               │   └───timekeeper
│               │       └───dto
│               ├───config
│               ├───consultant
│               │   └───dto
│               ├───demo
│               ├───exceptions
│               ├───meetings
│               │   └───dto
│               ├───messages
│               ├───notification
│               ├───populatedb
│               ├───reddays
│               │   └───dto
│               ├───registeredtime
│               │   └───dto
│               ├───responsiblept
│               ├───tag
│               ├───timechunks
│               └───utils
└───resources

```
<details><summary>Folder details</summary>

 >client - nager ⟶ fetching red days for Sweden and Norway 
 > (currently Salt has still 4 developers from Norawy).
 > Red days are being fetch to correctly calculate remaining time for each consultant.
 > We fetch from 2018 till now + 1 year. It is scheduled to run every year 1 January.
 
> client - notion ⟶ 

</details>

## $\color{rgb(107, 187, 242)}{\textsf{Future features}}$

- scheduled meetings notification system
- custom color picker for gantt-chart from legend level
- integration with Lucca




