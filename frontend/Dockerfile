# Step 1: Use an official Node.js runtime as a parent image
FROM node:18-alpine AS build

# Set the working directory in the container
WORKDIR /app

# Step 3: Copy package.json and package-lock.json to install dependencies
COPY package*.json ./

RUN npm install

COPY . .

# Accept build arguments
ARG VITE_CLERK_PUBLISHABLE_KEY
ARG VITE_BACKEND_URL
ENV VITE_CLERK_PUBLISHABLE_KEY=${VITE_CLERK_PUBLISHABLE_KEY}
ENV VITE_BACKEND_URL=${VITE_BACKEND_URL}

# Set the build environment variable (optional)
RUN echo "VITE_CLERK_PUBLISHABLE_KEY=" $VITE_CLERK_PUBLISHABLE_KEY
RUN echo "VITE_BACKEND_URL=" $VITE_BACKEND_URL

#  Build the React app using Vite
RUN npm run build

# Use an official Nginx image to serve the built files
FROM nginx:alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy the build output from the previous stage to Nginx's default public directory
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 8080

# Step 10: Start Nginx when the container launches
CMD ["nginx", "-g", "daemon off;"]

