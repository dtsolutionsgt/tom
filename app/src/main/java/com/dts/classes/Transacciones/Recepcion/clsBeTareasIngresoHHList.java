package com.dts.classes.Transacciones.Recepcion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeTareasIngresoHHList {
    @ElementList(inline=true,required=false)
    public List<clsBeTareasIngresoHH> items = new List<clsBeTareasIngresoHH>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<clsBeTareasIngresoHH> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(clsBeTareasIngresoHH clsBeTareasIngresoHH) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends clsBeTareasIngresoHH> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeTareasIngresoHH> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public clsBeTareasIngresoHH get(int index) {
            return null;
        }

        @Override
        public clsBeTareasIngresoHH set(int index, clsBeTareasIngresoHH element) {
            return null;
        }

        @Override
        public void add(int index, clsBeTareasIngresoHH element) {

        }

        @Override
        public clsBeTareasIngresoHH remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<clsBeTareasIngresoHH> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeTareasIngresoHH> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeTareasIngresoHH> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}

