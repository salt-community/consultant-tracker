# Step 1: Use an official Node.js runtime as a parent image
FROM node:18-alpine AS build

# Step 2: Set the working directory in the container
WORKDIR /app

# Step 3: Copy package.json and package-lock.json to install dependencies
COPY package*.json ./


# ARG VITE_BACKEND_URL
ARG VITE_CLERK_PUBLISHABLE_KEY
ENV CLERK_PUBLISHABLE_KEY=VITE_CLERK_PUBLISHABLE_KEY

# ARG THE_VAR_FROM_YOUR_DOT_ENV_FILE
# ENV THE_VAR_YOUR_NEXT_EXPECTS=${THE_VAR_FROM_YOUR_DOT_ENV_FILE}

# Step 4: Install dependencies
RUN npm install

# Step 5: Copy the rest of the application code
COPY . .

# Debug step: list files in /app
RUN ls -la /app/dist

# Step 6: Build the React app using Vite
RUN npm run build
# VITE_CLERK_PUBLISHABLE_KEY=${VITE_CLERK_PUBLISHABLE_KEY}

# Step 7: Use an official Nginx image to serve the built files
FROM nginx:alpine

# Step 8: Copy the build output from the previous stage to Nginx's default public directory
COPY --from=build /app/dist /usr/share/nginx/html

# Step 9: Expose port 80 to the outside world
EXPOSE 80

# Step 10: Start Nginx when the container launches
CMD ["nginx", "-g", "daemon off;"]