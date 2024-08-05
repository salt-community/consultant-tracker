export const selectColor = (type: string) => {
  switch (type) {
    case "Konsult-tid":
      return "#6EACDA";
    case "Semester":
      return "#F3FEB8";
    case "Sjuk":
      return "#99df8d";
    case "Remaining Days":
      return "#405D72";
    default:
      return "#EF5A6F"
  }
}