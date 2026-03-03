#!/usr/bin/env bash
set -euo pipefail

if [[ $# -ne 2 ]]; then
  echo "Usage: $0 <release-version> <next-snapshot-version>" >&2
  exit 1
fi

release_version="$1"
next_snapshot_version="$2"

./mvnw versions:set -DnewVersion="${release_version}"
git commit -a -m "Bump version to ${release_version}"
git tag "v${release_version}" -m "Release v${release_version}"
./mvnw versions:set -DnewVersion="${next_snapshot_version}"
git commit -a -m "Increment version to ${next_snapshot_version}"
