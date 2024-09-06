import { clerkMiddleware, createRouteMatcher } from '@clerk/nextjs/server'

const isPublicRoute = createRouteMatcher(['/sign-in'])
const isProtectedRoute = createRouteMatcher(['/home'])

export default clerkMiddleware((auth, request) => {
  if (!isPublicRoute(request)) {
    auth().protect()
  }
  if(isProtectedRoute){
    auth().protect()
  }
})