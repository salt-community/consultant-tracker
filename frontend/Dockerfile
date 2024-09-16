# # Use a Node.js base image
# FROM node:20-alpine

# # Set the working directory
# WORKDIR /app

# # Copy package.json and package-lock.json
# COPY package*.json ./

# # Accept build-time arguments and set environment variables
# # ARG NEXT_PUBLIC_BACKEND_URL
# # ENV NEXT_PUBLIC_BACKEND_URL=${NEXT_PUBLIC_BACKEND_URL}

# # Install dependencies
# RUN npm install

# # Copy the rest of the application code
# COPY . .
# # Build the Next.js app, injecting the environment variables at build-time
# RUN npm run build

# # Expose the port your app listens on (usually 3000)
# EXPOSE 3000

# # Define the command to run your app
# CMD ["npm", "start"]
# Step 1: Use an official Node.js runtime as a parent image
FROM node:14-alpine AS build

# Step 2: Set the working directory in the container
WORKDIR /app

# Step 3: Copy package.json and package-lock.json to install dependencies
COPY package*.json ./


ARG VITE_BACKEND_URL
ARG VITE_CLERK_PUBLISHABLE_KEY
ENV VITE_CLERK_PUBLISHABLE_KEY=${VITE_CLERK_PUBLISHABLE_KEY}
ENV VITE_BACKEND_URL=${VITE_BACKEND_URL}

# Step 4: Install dependencies
RUN npm install

# Step 5: Copy the rest of the application code
COPY . .

# Step 6: Build the React app using Vite
RUN npm run build

# Step 7: Use an official Nginx image to serve the built files
FROM nginx:alpine

# Step 8: Copy the build output from the previous stage to Nginx's default public directory
COPY --from=build /app/dist /usr/share/nginx/html

# Step 9: Expose port 80 to the outside world
EXPOSE 80

# Step 10: Start Nginx when the container launches
CMD ["nginx", "-g", "daemon off;"]