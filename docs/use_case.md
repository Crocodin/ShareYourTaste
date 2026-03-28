# SYT Feature Flow Diagram
This diagram illustrates the use case in the SYT (Share Your Taste) system.

> View [Use Case Description](.uml/UseCaseDescriptions.docx)

<p align="center">
  <img src=".uml/UseCaseDiagram_resize.png" alt="SYT Use Case Diagram" width="700" style="border-radius:10px; box-shadow:0 4px 12px rgba(0,0,0,0.15);">
</p>

# Non-functional requirements

### NFR-1 Scalabilit
> - The database should handle growth in songs, albums, and user ratings without requiring structural changes.
### NFR-2 Usability
> - The interface must be usable without any prior training
> - Error messages must be clear and guide the user toward a resolution
### NFR-3 Maintainability
> - The codebase must follow a consistent coding standard and be documented
### NFR-4 Arhitecture
> - The app will be built using layerd ahritecture scheme
> - We'll be using an SQL database


# Planification for three iterations

### 1'st iteration:
> - set up the database; including admin users and Albums & Songs <br>
> - configure the user hierarchy
> - configure the autentification part of the application <br>
> - make a basic view of the app in _Figma_

### 2'nd iteration:
> - configure crud operation for that application
> - repos & services

### 3'rd iteration:
> - configure the GUI
